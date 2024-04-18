package com.wlj.sportgoods.user.service.impl;

import com.wlj.sportgoods.sys.common.AppFileUtils;
import com.wlj.sportgoods.sys.common.WebUtils;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.user.entity.Message;
import com.wlj.sportgoods.user.mapper.MessageMapper;
import com.wlj.sportgoods.user.service.MessageService;
import com.wlj.sportgoods.user.vo.MessageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wlj
 * @since 2024-04-13
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Override
    public void addMsg(MessageVo messageVo) {

        if (messageVo.getImagePath() != null) {
            messageVo.setImagePath(AppFileUtils.renameFile(messageVo.getImagePath()));
        }
        this.save(messageVo);
    }

}
