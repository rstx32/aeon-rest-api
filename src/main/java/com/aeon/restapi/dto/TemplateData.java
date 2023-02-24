package com.aeon.restapi.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Setter
@Getter
public class TemplateData{
    private ResponseData responseData = new ResponseData();

    // success template parameter : payload and message
    public <T> ResponseEntity successTemplate(T payload, String message){
        this.responseData.setStatus("success");
        this.responseData.addMessage(message);
        this.responseData.setPayload(payload);

        return ResponseEntity.ok(responseData);
    }

    // fail template message parameter : string
    public <T> ResponseEntity failTemplate(String message){
        this.responseData.setStatus("fail");
        this.responseData.addMessage(message);
        this.responseData.setPayload(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    // fail template message parameter : list
    public <T> ResponseEntity failTemplate(List message){
        this.responseData.setStatus("fail");
        this.responseData.setMessages(message);
        this.responseData.setPayload(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }
}
