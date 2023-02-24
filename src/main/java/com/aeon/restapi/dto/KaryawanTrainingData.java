package com.aeon.restapi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KaryawanTrainingData {
    private String id;
    private String tanggal_training;
    private String karyawanId;
    private String trainingId;
}
