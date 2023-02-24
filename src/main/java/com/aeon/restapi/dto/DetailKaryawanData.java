package com.aeon.restapi.dto;

import com.aeon.restapi.models.entities.Karyawan;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailKaryawanData {
    private String id;
    private String nik;
    private String npwp;
    private Karyawan karyawan;
}
