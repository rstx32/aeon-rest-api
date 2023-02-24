package com.aeon.restapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RekeningData {
    private String id;
    private String jenis;
    private String nomor;
    private String nama;
    private String karyawanId;
}
