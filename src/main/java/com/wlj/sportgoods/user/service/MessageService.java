package com.wlj.sportgoods.user.service;

import com.wlj.sportgoods.user.entity.Message;
import com.wlj.sportgoods.user.vo.MessageVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wlj
 * @since 2024-04-13
 */
public interface MessageService extends IService<Message> {
    void saveOrReadMsg(MessageVo messageVo);
}
