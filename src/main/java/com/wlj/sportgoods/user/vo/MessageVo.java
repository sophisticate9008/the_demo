package com.wlj.sportgoods.user.vo;




import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wlj.sportgoods.user.entity.Message;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageVo extends Message{

    private Integer[] ids = null;
    private String type = null;

    public String toJsonString(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonMessage = mapper.writeValueAsString(this);
            return jsonMessage;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}