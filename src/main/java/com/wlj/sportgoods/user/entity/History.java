package com.wlj.sportgoods.user.entity;


import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wlj
 * @since 2024-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class History implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "account",type = IdType.INPUT)
    private String account;

    @TableId(value = "gid",type = IdType.INPUT)
    private Integer gid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date viewTime;

    private Integer star;

    private Integer available;

}
