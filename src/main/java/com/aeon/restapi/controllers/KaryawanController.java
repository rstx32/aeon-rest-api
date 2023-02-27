package com.aeon.restapi.controllers;

import com.aeon.restapi.dto.*;
import com.aeon.restapi.models.entities.DetailKaryawan;
import com.aeon.restapi.models.entities.Karyawan;
import com.aeon.restapi.models.entities.Rekening;
import com.aeon.restapi.service.DetailKaryawanService;
import com.aeon.restapi.service.KaryawanService;
import com.aeon.restapi.service.RekeningService;
import com.aeon.restapi.utils.FormValidationUtils;
import com.aeon.restapi.utils.ParseRequestToModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/v1/karyawan")
public class KaryawanController {
    @Autowired
    private KaryawanService karyawanService;

    @Autowired
    private DetailKaryawanService detailKaryawanService;

    @Autowired
    private RekeningService rekeningService;

    @PostMapping
    public ResponseEntity<ResponseData<Karyawan>> createKaryawan(@RequestBody KaryawanData karyawanData) {
        TemplateData templateData = new TemplateData();

        // karyawan validation
        ArrayList<String> karyawanErrorMessages = FormValidationUtils.checkKaryawan(karyawanData);
        if (!karyawanErrorMessages.isEmpty()) {
            return templateData.failTemplate(karyawanErrorMessages);
        }

        // detail karyawan validation
        DetailKaryawanData validateDk = karyawanData.getDetailKaryawan();
        ArrayList<String> detailKaryawanErrorMessages = FormValidationUtils.checkCreateDetailkaryawan(validateDk);
        if (!detailKaryawanErrorMessages.isEmpty()) {
            return templateData.failTemplate(detailKaryawanErrorMessages);
        }

        // save karyawan
        Karyawan parsedKaryawan = ParseRequestToModelUtils.parseKaryawan(karyawanData);
        Karyawan karyawan = karyawanService.createKaryawan(parsedKaryawan);

        // save detail karyawan
        DetailKaryawan parsedDetailKaryawan = ParseRequestToModelUtils.parseDetailKaryawan(karyawanData.getDetailKaryawan());
        parsedDetailKaryawan.setKaryawan(karyawan);
        detailKaryawanService.createDetailKaryawan(parsedDetailKaryawan);

        return templateData.successTemplate(karyawan, "success add new karyawan");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseData<Karyawan>> getSingleKaryawan(@PathVariable("id") String id) {
        TemplateData templateData = new TemplateData();

        // if id is not number
        if (!id.matches("^\\d+$")) {
            return templateData.failTemplate("id must be number");
        }

        Karyawan karyawan = karyawanService.findKaryawan(Long.valueOf(id));
        if (karyawan == null) {
            return templateData.failTemplate("karyawan not found");
        } else {
            return templateData.successTemplate(karyawan, "success get karyawan");
        }
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<ResponseData<Karyawan>> getDetailKaryawan(@PathVariable("id") String id) {
        TemplateData templateData = new TemplateData();

        // if id is not number
        if (!id.matches("^\\d+$")) {
            return templateData.failTemplate("id must be number");
        }

        Karyawan karyawan = karyawanService.findKaryawan(Long.valueOf(id));
        if (karyawan == null) {
            return templateData.failTemplate("karyawan not found");
        } else {
            DetailKaryawan detailKaryawan = detailKaryawanService.findDetailKaryawanByKaryawanID(Long.valueOf(id));
            return templateData.successTemplate(detailKaryawan, "success get detail karyawan");
        }
    }

    @PutMapping
    public ResponseEntity<ResponseData<Karyawan>> updateKaryawan(@RequestBody KaryawanData karyawanData) {
        TemplateData templateData = new TemplateData();

        // if karyawan id is not inserted
        if (karyawanData.getId() == null || karyawanData.getId().isBlank()) {
            return templateData.failTemplate("karyawan id is required");
        }

        // if karyawan id is not numeric
        if (!karyawanData.getId().matches("^\\d+$")) {
            return templateData.failTemplate("karyawan id invalid");
        }

        // if karyawan is not found / deleted
        Karyawan karyawan = karyawanService.findKaryawan(Long.valueOf(karyawanData.getId()));
        if (karyawan == null) {
            return templateData.failTemplate("karyawan is not found");
        }

        // karyawan validation
        ArrayList<String> karyawanErrorMessages = FormValidationUtils.checkKaryawan(karyawanData);
        if (!karyawanErrorMessages.isEmpty()) {
            return templateData.failTemplate(karyawanErrorMessages);
        }

        // detail karyawan
        DetailKaryawanData detailKaryawanData = karyawanData.getDetailKaryawan();
        if (detailKaryawanData != null) {
            // detail karyawan validation
            ArrayList<String> detailKaryawanErrorMessages = FormValidationUtils.checkUpdateDetailkaryawan(detailKaryawanData);
            if (!detailKaryawanErrorMessages.isEmpty()) {
                return templateData.failTemplate(detailKaryawanErrorMessages);
            }

            // update detail karyawan
            DetailKaryawan currentDetailKaryawan = detailKaryawanService.findDetailKaryawanByKaryawanID(karyawan.getId());
            DetailKaryawan parsedDetailKaryawan = ParseRequestToModelUtils.parseDetailKaryawan(detailKaryawanData);

            parsedDetailKaryawan.setKaryawan(currentDetailKaryawan.getKaryawan());
            parsedDetailKaryawan.setId(currentDetailKaryawan.getId());

            detailKaryawanService.updateDetailkaryawan(parsedDetailKaryawan);
        }

        // update karyawan
        Karyawan parsedKaryawan = ParseRequestToModelUtils.parseKaryawan(karyawanData);
        karyawanService.updateKaryawan(parsedKaryawan);

        return templateData.successTemplate(parsedKaryawan, "success update karyawan");
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseData<Iterable>> searchKaryawan(@RequestBody SearchData searchData) {
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
        Pageable pageable = PageRequest.of(page, size, Sort.by("nama").ascending());

        // sort descending
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by("nama").descending());
        }

        Iterable<Karyawan> karyawans = karyawanService.searchKaryawan(keyword, pageable);

        return templateData.successTemplate(karyawans, "success get karyawan");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Karyawan>> deleteKaryawan(@PathVariable("id") String id) {
        TemplateData templateData = new TemplateData();

        // if id is not number
        if (!id.matches("^\\d+$")) {
            return templateData.failTemplate("id must be number");
        }

        // if karyawan is not found / deleted
        Karyawan karyawan = karyawanService.findKaryawan(Long.valueOf(id));
        if (karyawan == null) {
            return templateData.failTemplate("karyawan not found");
        }

        karyawanService.deleteKaryawan(karyawan);

        return templateData.successTemplate(karyawan, "success delete karyawan");
    }

    @GetMapping(path = {"/{id_karyawan}/rekening", "/{id_karyawan}/rekening/{id_rekening}"})
    public ResponseEntity<ResponseData<Rekening>> getRekening(@PathVariable("id_karyawan") String id_karyawan, @PathVariable(value = "id_rekening") Optional<String> id_rekening) {
        TemplateData templateData = new TemplateData();

        // if id is not number
        if (!id_karyawan.matches("^\\d+$")) {
            return templateData.failTemplate("id karyawan must be number");
        }

        // if karyawan is not found / deleted
        Karyawan karyawan = karyawanService.findKaryawan(Long.valueOf(id_karyawan));
        if (karyawan == null) {
            return templateData.failTemplate("karyawan not found");
        }

        // if id rekening inserted
        if (id_rekening.isPresent()) {
            if (!id_rekening.get().matches("^\\d+$")) {
                return templateData.failTemplate("id rekening must be number");
            }

            Rekening rekening = rekeningService.findRekening(Long.valueOf(id_rekening.get()));
            if (rekening == null) {
                return templateData.failTemplate("rekening not found");
            } else {
                return templateData.successTemplate(rekening, "success get rekening");
            }
        }

        return templateData.successTemplate(rekeningService.getAllRekening(Long.valueOf(id_karyawan)), "success get all rekening");
    }

    @PostMapping("/{id_karyawan}/rekening")
    public ResponseEntity<ResponseData<Rekening>> createRekening(@PathVariable("id_karyawan") String id_karyawan, @RequestBody RekeningData rekeningData) {
        TemplateData templateData = new TemplateData();

        // if karyawan id is not numeric
        if (!id_karyawan.matches("^\\d+$")) {
            return templateData.failTemplate("karyawan id invalid");
        }

        // if karyawan is not found / deleted
        Karyawan karyawan = karyawanService.findKaryawan(Long.valueOf(id_karyawan));
        if (karyawan == null) {
            return templateData.failTemplate("karyawan is not found");
        }

        // rekening validation
        ArrayList<String> rekeningErrorMessages = FormValidationUtils.checkRekening(rekeningData);
        if (!rekeningErrorMessages.isEmpty()) {
            return templateData.failTemplate(rekeningErrorMessages);
        }

        // save rekening
        Rekening parsedRekening = ParseRequestToModelUtils.parseRekening(rekeningData);
        parsedRekening.setKaryawan(karyawan);
        Rekening rekening = rekeningService.createRekening(parsedRekening);

        return templateData.successTemplate(rekening, "success add new rekening");
    }

    @PutMapping("/{id_karyawan}/rekening")
    public ResponseEntity<ResponseData<Rekening>> updateRekening(@PathVariable("id_karyawan") String id_karyawan, @RequestBody RekeningData rekeningData) {
        TemplateData templateData = new TemplateData();

        // if karyawan id is not numeric
        if (!id_karyawan.matches("^\\d+$")) {
            return templateData.failTemplate("karyawan id invalid");
        }

        // if karyawan is not found / deleted
        Karyawan karyawan = karyawanService.findKaryawan(Long.valueOf(id_karyawan));
        if (karyawan == null) {
            return templateData.failTemplate("karyawan is not found");
        }

        // if rekening id is not inserted
        if (rekeningData.getId() == null || rekeningData.getId().isBlank()) {
            return templateData.failTemplate("rekening id is required");
        }

        // if rekening id is not numeric
        if (!rekeningData.getId().matches("^\\d+$")) {
            return templateData.failTemplate("rekening id invalid");
        }

        // if rekening is not found / deleted
        Rekening isRekeningExist = rekeningService.findRekening(Long.valueOf(rekeningData.getId()));
        if (isRekeningExist == null) {
            return templateData.failTemplate("rekening is not found");
        }

        // rekening validation
        ArrayList<String> rekeningErrorMessages = FormValidationUtils.checkRekening(rekeningData);
        if (!rekeningErrorMessages.isEmpty()) {
            return templateData.failTemplate(rekeningErrorMessages);
        }

        // update rekening
        Rekening parsedRekening = ParseRequestToModelUtils.parseRekening(rekeningData);
        parsedRekening.setKaryawan(karyawan);
        Rekening rekening = rekeningService.updateRekening(parsedRekening);

        return templateData.successTemplate(rekening, "success update rekening");
    }

    @DeleteMapping("/{id_karyawan}/rekening/{id_rekening}")
    public ResponseEntity<ResponseData<Rekening>> deleteRekening(@PathVariable("id_rekening") String id_rekening) {
        TemplateData templateData = new TemplateData();

        // if id is not number
        if (!id_rekening.matches("^\\d+$")) {
            return templateData.failTemplate("id rekening must be number");
        }

        // if karyawan is not found / deleted
        Rekening rekening = rekeningService.findRekening(Long.valueOf(id_rekening));
        if (rekening == null) {
            return templateData.failTemplate("rekening not found");
        }

        rekeningService.deleteRekening(rekening);

        return templateData.successTemplate(rekening, "success delete rekening");
    }

    // testing
    @GetMapping("/hello-world")
    public String helloWorld() {
        return "hello world!";
    }
}
