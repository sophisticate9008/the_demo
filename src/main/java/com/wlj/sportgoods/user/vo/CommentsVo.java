package com.wlj.sportgoods.user.vo;

import com.wlj.sportgoods.user.entity.Comments;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommentsVo extends Comments{
    private Integer page = 1;
    private Integer limit= 10;
}
