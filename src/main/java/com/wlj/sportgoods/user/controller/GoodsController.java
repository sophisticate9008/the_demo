package com.wlj.sportgoods.user.controller;


import org.apache.commons.lang3.StringUtils;
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
import com.wlj.sportgoods.user.entity.Goods;
import com.wlj.sportgoods.user.service.GoodsService;
import com.wlj.sportgoods.user.vo.GoodsVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-02-27
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("loadAllGoods")
    public DataGridView loadAllGoods(GoodsVo goodsVo) {
        IPage<Goods> page = new Page<>(goodsVo.getPage(),goodsVo.getLimit());
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(goodsVo.getGoodName() != null, "good_name", goodsVo.getGoodName());
        queryWrapper.eq(goodsVo.getMerchant() != null, "merchant", goodsVo.getMerchant());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getLabel()), "label",goodsVo.getLabel());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getIntroduction()),"introduction", goodsVo.getIntroduction());
        goodsService.page(page, queryWrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @RequestMapping("addGoods")
    public ResultObj addGoods(@RequestBody Goods goods) {
        try {
            if (goods.getImagePath()!=null&&goods.getImagePath().endsWith("_temp")){
                String newName = AppFileUtils.renameFile(goods.getImagePath());
                goods.setImagePath(newName);
            }
            if(!goodsService.save(goods)) {
                AppFileUtils.removeFileByPath(goods.getImagePath());
            }
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }        
    }
    @RequestMapping("updateGoods")
    public ResultObj updateGoods(GoodsVo goodsVo){
        try {
            //商品图片不是默认图片
            if (!(goodsVo.getImagePath()!=null&&goodsVo.getImagePath().equals("/images/noDefaultImage.jpg"))){
                if (goodsVo.getImagePath().endsWith("_temp")){
                    String newName = AppFileUtils.renameFile(goodsVo.getImagePath());
                    goodsVo.setImagePath(newName);
                    //删除原先的图片
                    String oldPath = goodsService.getById(goodsVo.getId()).getImagePath();
                    AppFileUtils.removeFileByPath(oldPath);
                }
            }
            goodsService.updateById(goodsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    @RequestMapping("deleteGoods")
    public ResultObj deleteGoods(Integer id,String goodsimg){
        try {
            //删除商品的图片
            AppFileUtils.removeFileByPath(goodsimg);
            goodsService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }    

}

