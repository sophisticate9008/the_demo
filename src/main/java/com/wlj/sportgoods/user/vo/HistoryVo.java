package com.wlj.sportgoods.user.vo;

import java.time.LocalDateTime;

import com.wlj.sportgoods.user.entity.History;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class HistoryVo extends History{
    private Integer page = 1;
    private Integer limit= 10;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer[] ids;
    private Boolean showHistory;
    private Boolean showStar;
}

