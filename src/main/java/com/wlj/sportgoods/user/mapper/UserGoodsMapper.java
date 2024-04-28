package com.wlj.sportgoods.user.mapper;

import com.wlj.sportgoods.user.entity.UserGoods;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wlj
 * @since 2024-02-07
 */

public interface UserGoodsMapper extends BaseMapper<UserGoods> {
    List<UserGoods> getRefoundApplymentByAccount(@Param("account") String account);
    Integer getSalesByGid(@Param("gid") Integer gid);

    List<UserGoods> getOrdersByAccount(@Param("account") String account);
    Integer getSalesDataByGid(@Param("gid") Integer gid, @Param("currentDate") Timestamp currentDate, @Param("nextDate") Timestamp nextDate);

}
