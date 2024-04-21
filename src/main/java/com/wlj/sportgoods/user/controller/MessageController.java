package com.wlj.sportgoods.user.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    @RequestMapping("changeSel") 
    public DataGridView changeSel(String sel) {
        User user = (User) WebUtils.getSession().getAttribute("user");
        webSocketManager.changeSel(user.getAccount(), sel);
        return new DataGridView("修改成功");
    }
    @RequestMapping("getRandomCustomerService")
    public DataGridView getRandomCustomerService(String merchant) {
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
        Comparator<Map.Entry<String, List<Message>>> comparator = new Comparator<Map.Entry<String, List<Message>>>() {
            @Override
            public int compare(Map.Entry<String, List<Message>> entry1, Map.Entry<String, List<Message>> entry2) {
                List<Message> list1 = entry1.getValue();
                List<Message> list2 = entry2.getValue();

                // 获取列表中最后一项的 Date
                Date date1 = list1.isEmpty() ? null : list1.get(list1.size() - 1).getSendTime();
                Date date2 = list2.isEmpty() ? null : list2.get(list2.size() - 1).getSendTime();

                // 比较日期大小
                if (date1 == null && date2 == null) {
                    return 0;
                } else if (date1 == null) {
                    return -1;
                } else if (date2 == null) {
                    return 1;
                } else {
                    return date1.compareTo(date2);
                }
            }
        };
        List<Map.Entry<String, List<Message>>> entries = new ArrayList<>(Msgs.entrySet());
        Collections.sort(entries, comparator);
        Map<String, List<Message>> sortedMsgs = new LinkedHashMap<>();
        for (Map.Entry<String, List<Message>> entry : entries) {
            sortedMsgs.put(entry.getKey(), entry.getValue());
        }
        return new DataGridView(sortedMsgs);

    }
    @RequestMapping("loadAllUserChat")
    public DataGridView loadAllUserChat() {
        User user = (User) WebUtils.getSession().getAttribute("user");
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant", user.getMerchant());
        List<Message> msgList = messageService.list(queryWrapper);
        return new DataGridView(msgList);
    }
}
