package com.aeon.restapi.controllers;

import com.aeon.restapi.dto.KaryawanTrainingData;
import com.aeon.restapi.dto.ResponseData;
import com.aeon.restapi.dto.SearchData;
import com.aeon.restapi.dto.TemplateData;
import com.aeon.restapi.models.entities.Karyawan;
import com.aeon.restapi.models.entities.KaryawanTraining;
import com.aeon.restapi.models.entities.Training;
import com.aeon.restapi.service.KaryawanService;
import com.aeon.restapi.service.KaryawanTrainingService;
import com.aeon.restapi.service.TrainingService;
import com.aeon.restapi.utils.FormValidationUtils;
import com.aeon.restapi.utils.ParseRequestToModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1/training-karyawan")
public class KaryawanTrainingController {
    @Autowired
    private KaryawanTrainingService karyawanTrainingService;

    @Autowired
    private KaryawanService ks;

    @Autowired
    private TrainingService ts;

    @PostMapping
    public ResponseEntity<ResponseData<KaryawanTraining>> createKaryawanTraining(@RequestBody KaryawanTrainingData karyawanTrainingData) {
        TemplateData templateData = new TemplateData();

        // karyawan training validation
        ArrayList<String> karyawanTrainingErrorMessages = FormValidationUtils.checkKaryawanTraining(karyawanTrainingData);
        if (!karyawanTrainingErrorMessages.isEmpty()) {
            return templateData.failTemplate(karyawanTrainingErrorMessages);
        }

        // if karyawan or training not found
        Karyawan karyawan = ks.findKaryawan(Long.valueOf(karyawanTrainingData.getKaryawanId()));
        Training training = ts.findTraining(Long.valueOf(karyawanTrainingData.getTrainingId()));
        if (karyawan == null) {
            return templateData.failTemplate("karyawan with ID " + karyawanTrainingData.getKaryawanId() + " is not found");
        }
        if (training == null) {
            return templateData.failTemplate("training with ID " + karyawanTrainingData.getTrainingId() + " is not found");
        }

        // save karyawan training
        KaryawanTraining parsedKaryawanTraining = ParseRequestToModelUtils.parseKaryawanTraining(karyawanTrainingData);
        parsedKaryawanTraining.setKaryawan(karyawan);
        parsedKaryawanTraining.setTraining(training);

        karyawanTrainingService.createKaryawanTraining(parsedKaryawanTraining);

        return templateData.successTemplate(parsedKaryawanTraining, "success add new karyawan training");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<KaryawanTraining>> getSingleKaryawanTraining(@PathVariable("id") String id) {
        TemplateData templateData = new TemplateData();

        // if id is not number
        if (!id.matches("^\\d+$")) {
            return templateData.failTemplate("id must be number");
        }

        KaryawanTraining karyawanTraining = karyawanTrainingService.findKaryawanTraining(Long.valueOf(id));
        if (karyawanTraining == null) {
            return templateData.failTemplate("karyawan training not found");
        } else {
            return templateData.successTemplate(karyawanTraining, "success get karyawan training");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseData<Iterable>> searchKaryawanTraining(@RequestBody SearchData searchData) {
        TemplateData templateData = new TemplateData();

        // search validation
        ArrayList<String> searchErrorMessages = FormValidationUtils.checkSearch(searchData);
        if (!searchErrorMessages.isEmpty()) {
            return templateData.failTemplate(searchErrorMessages);
        }

        String keyword = searchData.getKeyword();
        String sort = searchData.getSort();
        int page = Integer.valueOf(searchData.getPage());
        int size = Integer.valueOf(searchData.getSize());

        // default sort : ascending
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        // sort descending
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("id").descending());
        }

        Iterable<KaryawanTraining> karyawanTrainings = karyawanTrainingService.searchKaryawanTraining(keyword, pageable);

        return templateData.successTemplate(karyawanTrainings, "success get karyawan training");
    }

    @PutMapping
    public ResponseEntity<ResponseData<KaryawanTraining>> updateKaryawanTraining(@RequestBody KaryawanTrainingData karyawanTrainingData) {
        TemplateData templateData = new TemplateData();

        // if karyawan training id is not inserted
        if (karyawanTrainingData.getId() == null || karyawanTrainingData.getId().isBlank()) {
            return templateData.failTemplate("karyawan training id is required");
        }

        // if id is not numeric
        if (!karyawanTrainingData.getId().matches("^\\d+$")) {
            return templateData.failTemplate("karyawan training id invalid");
        }

        // if karyawan training is not found / deleted
        KaryawanTraining karyawanTraining = karyawanTrainingService.findKaryawanTraining(Long.valueOf(karyawanTrainingData.getId()));
        if (karyawanTraining == null) {
            return templateData.failTemplate("karyawan training is not found");
        }

        // karyawan training validation
        ArrayList<String> karyawanTrainingErrorMessages = FormValidationUtils.checkKaryawanTraining(karyawanTrainingData);
        if (!karyawanTrainingErrorMessages.isEmpty()) {
            return templateData.failTemplate(karyawanTrainingErrorMessages);
        }

        // update karyawan training
        KaryawanTraining parsedKaryawanTraining = ParseRequestToModelUtils.parseKaryawanTraining(karyawanTrainingData);

        // if karyawan or training not found in database
        if (karyawanTrainingData.getKaryawanId() != null) {
            Karyawan karyawan = ks.findKaryawan(Long.valueOf(karyawanTrainingData.getKaryawanId()));
            if (ks.findKaryawan(Long.valueOf(karyawanTrainingData.getKaryawanId())) == null) {
                return templateData.failTemplate("karyawan with ID " + karyawanTrainingData.getKaryawanId() + " is not found");
            }
            if (karyawanTrainingData.getKaryawanId() != null) {
                parsedKaryawanTraining.setKaryawan(karyawan);
            }
        }

        if (karyawanTrainingData.getTrainingId() != null) {
            Training training = ts.findTraining(Long.valueOf(karyawanTrainingData.getTrainingId()));
            if (ts.findTraining(Long.valueOf(karyawanTrainingData.getTrainingId())) == null) {
                return templateData.failTemplate("training with ID " + karyawanTrainingData.getTrainingId() + " is not found");
            }
            if (karyawanTrainingData.getTrainingId() != null) {
                parsedKaryawanTraining.setTraining(training);
            }
        }

        karyawanTrainingService.updateKaryawanTraining(parsedKaryawanTraining);

        return templateData.successTemplate(parsedKaryawanTraining, "success update karyawan training");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<KaryawanTraining>> deleteKaryawanTraining(@PathVariable("id") String id) {
        TemplateData templateData = new TemplateData();

        // if id is not number
        if (!id.matches("^\\d+$")) {
            return templateData.failTemplate("id must be number");
        }

        // if karyawan-training is not found / deleted
        KaryawanTraining karyawanTraining = karyawanTrainingService.findKaryawanTraining(Long.valueOf(id));
        if (karyawanTraining == null) {
            return templateData.failTemplate("karyawan training not found");
        }

        karyawanTrainingService.deleteKaryawanTraining(karyawanTraining);

        return templateData.successTemplate(karyawanTraining, "success delete karyawan training");
    }
}
