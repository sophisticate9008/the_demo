package com.wlj.sportgoods.sys.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.wlj.sportgoods.sys.common.AppFileUtils;
import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.sys.entity.Carousel;
import com.wlj.sportgoods.sys.service.CarouselService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-03-10
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @RequestMapping("addCarousel")
    @RequiresPermissions({"*:*"})
    public ResultObj addCarousel(@RequestBody Carousel carousel) {
        String newPath = AppFileUtils.renameFile(carousel.getImagePath());
        carousel.setImagePath(newPath);
        if(carouselService.save(carousel)) {
            return ResultObj.ADD_SUCCESS;
        }else {
            AppFileUtils.removeFileByPath(newPath);
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("deleteCarousel")
    @RequiresPermissions({"*:*"})
    public ResultObj deleteCarousel(@RequestBody Carousel carousel) {
        if(carouselService.removeById(carousel.getId())) {
            return ResultObj.DELETE_SUCCESS;
        }else {
            return ResultObj.DELETE_ERROR;
        }
    }

    @RequestMapping("getCarousel")
    public DataGridView getCarousel() {
        return new DataGridView(carouselService.list());
    }
}

