package com.aeon.restapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KaryawanData {
    private String id;
    private String nama;
    private String jk;
    private String status;
    private String alamat;
    private String dob;
    private DetailKaryawanData detailKaryawan;
}
