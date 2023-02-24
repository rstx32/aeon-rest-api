package com.aeon.restapi.utils;

import com.aeon.restapi.dto.*;
import com.aeon.restapi.models.entities.*;

import java.time.LocalDate;

public class ParseRequestToModelUtils {
    public static Karyawan parseKaryawan(KaryawanData karyawanData) {
        Karyawan karyawan = new Karyawan();

        // if requestData value not empty, then update value
        if (karyawanData.getId() != null) {
            karyawan.setId(Long.valueOf(karyawanData.getId()));
        }

        if (karyawanData.getNama() != null) {
            karyawan.setNama(karyawanData.getNama());
        }

        if (karyawanData.getJk() != null) {
            karyawan.setJk(karyawanData.getJk());
        }

        if (karyawanData.getStatus() != null) {
            karyawan.setStatus(karyawanData.getStatus());
        }

        if (karyawanData.getAlamat() != null) {
            karyawan.setAlamat(karyawanData.getAlamat());
        }

        if (karyawanData.getDob() != null) {
            karyawan.setDob(LocalDate.parse(karyawanData.getDob()));
        }

        return karyawan;
    }

    public static DetailKaryawan parseDetailKaryawan(DetailKaryawanData detailKaryawanData) {
        DetailKaryawan detailKaryawan = new DetailKaryawan();

        // if requestData value not empty, then update value
        if (detailKaryawanData.getId() != null) {
            detailKaryawan.setId(Long.valueOf(detailKaryawanData.getId()));
        }

        if (detailKaryawanData.getNik() != null) {
            detailKaryawan.setNik(detailKaryawanData.getNik());
        }

        if (detailKaryawanData.getNpwp() != null) {
            detailKaryawan.setNpwp(detailKaryawanData.getNpwp());
        }

        detailKaryawan.setKaryawan(detailKaryawan.getKaryawan());

        return detailKaryawan;
    }

    public static Training parseTraining(TrainingData trainingData) {
        Training training = new Training();

        // if requestData value not empty, then update value
        if (trainingData.getId() != null) {
            training.setId(Long.valueOf(trainingData.getId()));
        }

        if (trainingData.getNama_pengajar() != null) {
            training.setNama_pengajar(trainingData.getNama_pengajar());
        }

        if (trainingData.getTema() != null) {
            training.setTema(trainingData.getTema());
        }

        return training;
    }

    public static KaryawanTraining parseKaryawanTraining(KaryawanTrainingData karyawanTrainingData) {
        KaryawanTraining karyawanTraining = new KaryawanTraining();

        // if requestData value not empty, then update value
        if (karyawanTrainingData.getId() != null) {
            karyawanTraining.setId(Long.valueOf(karyawanTrainingData.getId()));
        }

        if (karyawanTrainingData.getTanggal_training() != null) {
            karyawanTraining.setTanggal_training(LocalDate.parse(karyawanTrainingData.getTanggal_training()));
        }

        return karyawanTraining;
    }

    public static Rekening parseRekening(RekeningData rekeningData) {
        Rekening rekening = new Rekening();

        // if requestData value not empty, then update value
        if (rekeningData.getId() != null) {
            rekening.setId(Long.valueOf(rekeningData.getId()));
        }

        if(rekeningData.getNama() != null){
            rekening.setNama(rekeningData.getNama());
        }

        if(rekeningData.getNomor() != null){
            rekening.setNomor(rekeningData.getNomor());
        }

        if(rekeningData.getJenis() != null){
            rekening.setJenis(rekeningData.getJenis());
        }

        return rekening;
    }
}
