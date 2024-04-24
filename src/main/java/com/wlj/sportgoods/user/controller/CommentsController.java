package com.wlj.sportgoods.user.controller;


import java.util.Date;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlj.sportgoods.sys.common.AppFileUtils;
import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.sys.common.WebUtils;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.user.entity.Comments;
import com.wlj.sportgoods.user.entity.Goods;
import com.wlj.sportgoods.user.service.CommentsService;
import com.wlj.sportgoods.user.service.GoodsService;
import com.wlj.sportgoods.user.service.UserGoodsService;
import com.wlj.sportgoods.user.vo.CommentsVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-02-06
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @Autowired
    private UserGoodsService userGoodsService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("loadAllComment")
    public DataGridView loadAllComment(@RequestBody CommentsVo commentsVo) {
        
        User user = (User) WebUtils.getSession().getAttribute("user");

        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        if(user != null) {
            queryWrapper.eq(commentsVo.getShowMyComments(),"account", user.getAccount());            
        }
        queryWrapper.eq(commentsVo.getId() != null, "id", commentsVo.getId());
        queryWrapper.ne(!commentsVo.getIsReply(), "reply_id", -1);
        queryWrapper.eq(commentsVo.getGid() != null,"gid", commentsVo.getGid());
        queryWrapper.eq(commentsVo.getStar() != null,"star", commentsVo.getStar());
        queryWrapper.orderByDesc("create_time");
        return new DataGridView(commentsService.list(queryWrapper));
    }

    @RequestMapping("addComment")
    public ResultObj addComment(@RequestBody CommentsVo commentsVo) {
        Comments theComments;
        User user = (User) WebUtils.getSession().getAttribute("user");
        boolean hasBought = userGoodsService.hasBoughtGoods(user.getAccount(), commentsVo.getGid());
        String[] imgPaths = commentsVo.getImagePath().split(";");
        String pathResult = "";
        for (String item : imgPaths) {
            pathResult += ";" + AppFileUtils.renameFile(item);
        }
        
        if(commentsVo.getTheId() != null) {
            if(user.getType() != 3) {
                for (String item : imgPaths) {
                    AppFileUtils.removeFileByPath(item.replace("_temp", ""));
                }
                return ResultObj.EXCEED_PERMISSION;
                
            }
            Goods theGood = goodsService.getById(commentsVo.getGid());
            if(!theGood.getMerchant().equals(user.getMerchant())) {
                for (String item : imgPaths) {
                    AppFileUtils.removeFileByPath(item.replace("_temp", ""));
                }
                return ResultObj.EXCEED_PERMISSION;
            }
        }

        if(hasBought || user.getType() == 3) {
            commentsVo.setAccount(user.getAccount());
            commentsVo.setCreateTime(new Date());
            commentsVo.setImagePath(pathResult.replace(";;", ""));
            if(commentsVo.getTheId() != null) {
                commentsVo.setReplyId(-1);
                commentsService.save(commentsVo);
                theComments = commentsService.getById(commentsVo.getTheId());
                theComments.setReplyId(commentsVo.getId());
                commentsService.updateById(theComments);
            }else {
                commentsService.save(commentsVo);
            }

            return ResultObj.ADD_SUCCESS;
        }else {
            for (String item : imgPaths) {
                AppFileUtils.removeFileByPath(item.replace("_temp", ""));
            }
            return ResultObj.EXCEED_PERMISSION;
        } 
    }

    @RequestMapping("deleteComment")
    @RequiresPermissions(value = {"user:deleteComment", "*:*"},logical = Logical.OR)
    public ResultObj deleteComment(@RequestBody CommentsVo commentsVo) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        Comments thComments = commentsService.getById(commentsVo.getId());
        if(!thComments.getAccount().equals(user.getAccount()) && user.getType() != 4) {
            return ResultObj.EXCEED_PERMISSION;
        }else {
            commentsService.removeById(commentsVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }
    }

}

