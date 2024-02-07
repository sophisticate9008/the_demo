package com.wlj.sportgoods.user.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.user.entity.Comments;
import com.wlj.sportgoods.user.mapper.CommentsMapper;
import com.wlj.sportgoods.user.service.CommentsService;
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
    private CommentsMapper commentsMapper;

    @RequestMapping("loadAllComment")
    public DataGridView loadAllComment(CommentsVo commentsVo) {
        IPage<Comments> page = new Page<>(commentsVo.getPage(),commentsVo.getLimit());
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(commentsVo.getGid() != null,"gid", commentsVo.getGid());
        queryWrapper.eq(commentsVo.getStar() != null,"star", commentsVo.getStar());
        queryWrapper.orderByDesc("create_time");
        commentsService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @RequestMapping("addComment")
    public ResultObj addComment(CommentsVo commentsVo) {
        boolean hasBought = userGoodsService.hasBoughtGoods(commentsVo.getAccount(), commentsVo.getGid());
        if(hasBought) {
            commentsService.save(commentsVo);
            return ResultObj.ADD_SUCCESS;
        }else {
            return ResultObj.ADD_ERROR;
        } 
    }

    @RequestMapping("deleteComment")
    public ResultObj deleteComment(CommentsVo commentsVo) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", commentsVo.getAccount());
        queryWrapper.eq("gid", commentsVo.getGid());
        int count = commentsMapper.selectCount(queryWrapper);
        if(count > 0) {
            commentsService.removeById(commentsVo.getId());
            return ResultObj.DELETE_SUCCESS;
        }else {
            return ResultObj.DELETE_ERROR;
        }
    }

}

