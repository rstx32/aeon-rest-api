package com.aeon.restapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchData {
    private String keyword;
    private String size;
    private String page;
    private String sort;

}
