package com.wlj.sportgoods.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wlj.sportgoods.sys.common.DataGridView;
import com.wlj.sportgoods.sys.common.WebSocketManager;
import com.wlj.sportgoods.sys.common.WebUtils;
import com.wlj.sportgoods.sys.entity.User;
import com.wlj.sportgoods.sys.service.UserService;
import com.wlj.sportgoods.user.entity.Message;
import com.wlj.sportgoods.user.service.MessageService;
import com.wlj.sportgoods.user.vo.MessageVo;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wlj
 * @since 2024-04-13
 */
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private WebSocketManager webSocketManager;

    @Autowired
    private UserService userService;

    @RequestMapping("getRandomCustomerService")
    public DataGridView getRandomCustomerService(String merchant) {
        log.info("[]" + merchant);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant", merchant);
        List<User> customerServices = userService.list(queryWrapper);
        List<User> onlinList = new ArrayList<>();
        for (User customerService : customerServices) {
            if (webSocketManager.getuuid(customerService.getAccount()) != null) {
                onlinList.add(customerService);
            }
        }
        if (onlinList.isEmpty()) {
            return new DataGridView("无在线客服,请稍后刷新重试");
        } else {
            Random random = new Random();
            int randomIndex = random.nextInt(onlinList.size()); // 生成一个范围在 [0, size-1] 之间的随机数
            User randomUser = onlinList.get(randomIndex);
            return new DataGridView(randomUser.getAccount());
        }

    }

    @RequestMapping("loadMsg")
    public DataGridView loadMsg() {
        User user = (User) WebUtils.getSession().getAttribute("user");
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        if (user.getType() == 1) {
            queryWrapper.eq("user", user.getAccount());
        } else {
            queryWrapper.eq("customerService", user.getAccount());
        }
        List<Message> msgList = messageService.list(queryWrapper);
        Map<String, List<Message>> Msgs = new HashMap<>();
        for (Message msg : msgList) {
            if (user.getType() == 1) {
                if (Msgs.containsKey(msg.getMerchant())) {
                    Msgs.get(msg.getMerchant()).add(msg);
                } else {
                    List<Message> msgs = new ArrayList<>();
                    msgs.add(msg);
                    Msgs.put(msg.getMerchant(), msgs);
                }
            } else {
                if (Msgs.containsKey(msg.getUser())) {
                    Msgs.get(msg.getUser()).add(msg);
                } else {
                    List<Message> msgs = new ArrayList<>();
                    msgs.add(msg);
                    Msgs.put(msg.getUser(), msgs);
                }
            }

        }
        Map<String, List<Message>> reversedMsgs = new HashMap<>();
        for (Map.Entry<String, List<Message>> entry : Msgs.entrySet()) {
            String key = entry.getKey();
            List<Message> value = entry.getValue();
            reversedMsgs.put(key, value); // 将原始键值对反转存储到新的 Map 中
        }
        return new DataGridView(Msgs);

    }

    @RequestMapping("read")
    public void readUser(@RequestBody MessageVo messageVo) {
        for (Integer id : messageVo.getIds()) {
            Message message = messageService.getById(id);
            if (messageVo.getType().equals("user")) {
                message.setHaveReadUser(1);
            } else {
                message.setHaveReadCustomerservice(1);
            }

            messageService.updateById(message);
        }

    }
}
