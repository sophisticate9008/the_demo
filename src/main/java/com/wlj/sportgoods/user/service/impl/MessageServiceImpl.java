package com.wlj.sportgoods.user.service.impl;

import com.wlj.sportgoods.sys.common.AppFileUtils;
import com.wlj.sportgoods.user.entity.Message;
import com.wlj.sportgoods.user.mapper.MessageMapper;
import com.wlj.sportgoods.user.service.MessageService;
import com.wlj.sportgoods.user.vo.MessageVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;

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
    public void saveOrReadMsg(MessageVo messageVo) {
        if(!messageVo.getType().equals("update")) {
            String[] imgPaths = messageVo.getImagePath().split(";");
            String pathResult = "";
            for (String item : imgPaths) {
                pathResult += ";" + AppFileUtils.renameFile(item);
            }
            messageVo.setSendTime(new Date());
            messageVo.setImagePath(pathResult.replace(";;", ""));
            this.save(messageVo);
        }else {
            int id = messageVo.getId();
            Message theMessage = this.getById(id);
            theMessage.setHaveReadCustomerservice(messageVo.getHaveReadCustomerservice());
            theMessage.setHaveReadUser(messageVo.getHaveReadUser());
            this.updateById(theMessage);
        }
    }

}
