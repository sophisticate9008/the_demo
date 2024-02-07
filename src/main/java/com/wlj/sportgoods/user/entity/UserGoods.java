package com.wlj.sportgoods.user.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wlj
 * @since 2024-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserGoods implements Serializable {

    private static final long serialVersionUID=1L;

    private String account;

    private Integer gid;

    private Integer num;

    @TableField("finishTime")
    private LocalDateTime finishTime;

    /**
     * 0未完成,也就是在购物车里. 1完成交易, -1退货
     */
    private Integer status;


}
