package com.wlj.sportgoods.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wlj.sportgoods.sys.common.WebSocketManager;
import com.wlj.sportgoods.user.service.MessageService;
import com.wlj.sportgoods.user.vo.MessageVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WebSocketController {

    @Autowired
    private WebSocketManager webSocketManager;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/init/{uuid}")
    public void handleConnect(@DestinationVariable String uuid, @Payload String userId) {
        log.info("接收到用户连接：{}", userId + " > " + uuid);
        webSocketManager.adduuid(userId, uuid);
    }

    @MessageMapping("/disconnect/{uuid}")
    public void handleDisconnect(@DestinationVariable String uuid, @Payload String userId) {
        if (webSocketManager.getuuid(userId).equals(uuid)) {
            log.info("用户断开连接：{}", userId);
            webSocketManager.removeuuid(userId);
            webSocketManager.removeSel(userId);
        }
    }

    @MessageMapping("/send/*")
    public void handleMessage(@Payload String message) throws JsonMappingException, JsonProcessingException {
        MessageVo messagevo = objectMapper.readValue(message, MessageVo.class);
        if (!messagevo.getType().equals("update")) {

            if (messagevo.getType().equals("customerService")) {
                if (webSocketManager.getSel(messagevo.getUser()) != null
                        && webSocketManager.getSel(messagevo.getUser()).equals(messagevo.getCustomerService())) {
                    messagevo.setHaveReadUser(1);
                }
            } else if (messagevo.getType().equals("user")) {
                if (webSocketManager.getSel(messagevo.getCustomerService()) != null
                        && webSocketManager.getSel(messagevo.getCustomerService()).equals(messagevo.getUser())) {
                    messagevo.setHaveReadCustomerservice(1);
                }
            }
            messageService.saveOrReadMsg(messagevo);
            message = objectMapper.writeValueAsString(messagevo);
            messagingTemplate.convertAndSend(
                    "/topic/messages/" + webSocketManager.getuuid(messagevo.getCustomerService()),
                    message);
            messagingTemplate.convertAndSend("/topic/messages/" + webSocketManager.getuuid(messagevo.getUser()),
                    message);
        }else {
            messageService.saveOrReadMsg(messagevo);
        }

    }
}
