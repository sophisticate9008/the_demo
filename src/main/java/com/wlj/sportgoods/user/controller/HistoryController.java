package com.wlj.sportgoods.user.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.sys.common.WebUtils;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.user.entity.History;
import com.wlj.sportgoods.user.service.HistoryService;
import com.wlj.sportgoods.user.vo.HistoryVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-03-01
 */
@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;
    
    @RequestMapping("updateHistory") 
    public ResultObj addHistory(@RequestBody History history) {
        User user = (User) WebUtils.getSession().getAttribute("user");

        history.setViewTime(new Date());
        history.setAccount(user.getAccount());
        history.setAvailable(1);
        if(historyService.saveOrUpdate(history)) {
            return ResultObj.UPDATE_SUCCESS;
        }else {
            return ResultObj.UPDATE_ERROR;
        }

    }
    @RequestMapping("getHistory")
    public DataGridView getHistory(@RequestBody HistoryVo historyVo) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        IPage<History> page = new Page<>(historyVo.getPage(), historyVo.getLimit());
        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", user.getAccount());
        queryWrapper.eq(historyVo.getShowHistory(),"available", 1);
        queryWrapper.eq(historyVo.getShowStar(), "star", 1);
        queryWrapper.ge(historyVo.getStartTime()!=null,"view_time", historyVo.getStartTime());
        queryWrapper.le(historyVo.getEndTime()!=null,"view_time", historyVo.getEndTime());
        queryWrapper.orderByDesc("view_time");
        historyService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @RequestMapping("deleteHistory")
    public ResultObj deleteHistory(@RequestBody HistoryVo historyVo) {
        try {
            User user = (User) WebUtils.getSession().getAttribute("user");
            historyVo.setAccount(user.getAccount());
            historyVo.setAvailable(0);
            Integer[] ids = historyVo.getIds();
            QueryWrapper<History> queryWrapper = new QueryWrapper<>();
            for(int i = 0; i < ids.length; i++) {
                historyVo.setGid(ids[i]);
                historyService.updateById(historyVo);
                queryWrapper.eq("gid", ids[i]);
                queryWrapper.eq("star", 0);
                if(!historyService.remove(queryWrapper)) {
                    historyService.updateById(historyVo);
                }
                
                
            }
            return ResultObj.DELETE_SUCCESS;
        }catch(Exception e) {
            return ResultObj.DELETE_ERROR;
        }

    }

}

