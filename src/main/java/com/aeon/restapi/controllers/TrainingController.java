package com.aeon.restapi.controllers;

import com.aeon.restapi.dto.ResponseData;
import com.aeon.restapi.dto.SearchData;
import com.aeon.restapi.dto.TemplateData;
import com.aeon.restapi.dto.TrainingData;
import com.aeon.restapi.models.entities.Training;
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
@RequestMapping("/v1/training")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    @PostMapping
    public ResponseEntity<ResponseData<Training>> createTraining(@RequestBody TrainingData trainingData) {
        TemplateData templateData = new TemplateData();

        // training validation
        ArrayList<String> trainingErrorMessages = FormValidationUtils.checkTraining(trainingData);
        if (!trainingErrorMessages.isEmpty()) {
            return templateData.failTemplate(trainingErrorMessages);
        }

        // save training
        Training parsedTraining = ParseRequestToModelUtils.parseTraining(trainingData);
        Training training = trainingService.createTraining(parsedTraining);

        return templateData.successTemplate(training, "success add new training");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Training>> getSingleTraining(@PathVariable("id") String id) {
        TemplateData templateData = new TemplateData();

        // if id is not number
        if (!id.matches("^\\d+$")) {
            return templateData.failTemplate("id must be number");
        }

        Training training = trainingService.findTraining(Long.valueOf(id));
        if (training == null) {
            return templateData.failTemplate("training not found");
        } else {
            return templateData.successTemplate(training, "success get training");
        }

    }

    @PutMapping
    public ResponseEntity<ResponseData<Training>> updateTraining(@RequestBody TrainingData trainingData) {
        TemplateData templateData = new TemplateData();

        // if training id is not inserted
        if (trainingData.getId() == null || trainingData.getId().isBlank()) {
            return templateData.failTemplate("training id is required");
        }

        // if id is not numeric
        if (!trainingData.getId().matches("^\\d+$")) {
            return templateData.failTemplate("training id invalid");
        }

        // if training is not found / deleted
        Training training = trainingService.findTraining(Long.valueOf(trainingData.getId()));
        if (training == null) {
            return templateData.failTemplate("training is not found");
        }

        // training validation
        ArrayList<String> trainingErrorMessages = FormValidationUtils.checkTraining(trainingData);
        if (!trainingErrorMessages.isEmpty()) {
            return templateData.failTemplate(trainingErrorMessages);
        }

        Training parsedTraining = ParseRequestToModelUtils.parseTraining(trainingData);
        trainingService.updateTraining(parsedTraining);

        return templateData.successTemplate(parsedTraining, "success update training");
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseData<Iterable>> searchTraining(@RequestBody SearchData searchData) {
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
        Pageable pageable = PageRequest.of(page, size, Sort.by("tema").ascending());

        // sort descending
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("tema").descending());
        }

        Iterable<Training> trainings = trainingService.searchTraining(keyword, pageable);

        return templateData.successTemplate(trainings, "success get training");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Training>> deleteTraining(@PathVariable("id") Long id) {
        TemplateData templateData = new TemplateData();

        // if training is not exist / deleted
        Training training = trainingService.findTraining(id);
        if (training == null) {
            return templateData.failTemplate("training not found");
        }

        trainingService.deleteTraining(training);

        return templateData.successTemplate(training, "success delete training");
    }
}
