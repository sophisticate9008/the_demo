package com.wlj.sportgoods.user.service.impl;

import com.wlj.sportgoods.sys.common.ResultObj;
import com.wlj.sportgoods.user.entity.Comments;
import com.wlj.sportgoods.user.mapper.CommentsMapper;
import com.wlj.sportgoods.user.service.CommentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.regexp.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wlj
 * @since 2024-02-06
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    
}
