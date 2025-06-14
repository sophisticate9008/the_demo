package com.wlj.sportgoods.sys.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wlj
 * @since 2024-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;
    @TableId(value = "account",type = IdType.INPUT)
    private String account;

    private String nickname;

    private String password;

    private String address;

    private String sex;

    private String merchant;

    private Integer type;

    private String avatarpath;

    private String salt;

    private Integer available;
    
    private BigDecimal gold;

}
