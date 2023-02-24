package com.aeon.restapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ResponseData<T> {
    private String status;
    private List<String> messages = new ArrayList<>();
    private T payload;

    public void addMessage(String message){
        this.getMessages().add(message);
    }
}
