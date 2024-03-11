package com.wlj.sportgoods.sys.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.sys.entity.Notice;
import com.wlj.sportgoods.sys.service.NoticeService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-02-06
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("addNotice")
    @RequiresPermissions({"superUser:addNotice"})
    public ResultObj addNotice(@RequestBody Notice notice) {
        if(noticeService.save(notice)) {
            return ResultObj.ADD_SUCCESS;
        }else {
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("deleteNotice")
    @RequiresPermissions({"*:*"})
    public ResultObj deleteNotice(@RequestBody Notice notice) {
        if(noticeService.removeById(notice.getId())) {
            return ResultObj.DELETE_SUCCESS;
        }else {
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("getNotice")
    public DataGridView getNotice() {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return new DataGridView(noticeService.list());
    }
}

