package com.aeon.restapi.utils;

import com.aeon.restapi.auth.AuthenticationRequest;
import com.aeon.restapi.auth.RegisterRequest;
import com.aeon.restapi.dto.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FormValidationUtils {
    public static boolean isValueEmpty(String field) {
        return (field == null || field.isEmpty() || field.isBlank());
    }

    public static ArrayList<String> checkKaryawan(KaryawanData k) {
        ArrayList<String> messages = new ArrayList<>();
        String id = k.getId();
        String nama = k.getNama();
        String status = k.getStatus();
        String jk = k.getJk();
        String alamat = k.getAlamat();
        String dob = k.getDob();

        // this condition only for CREATE
        if (id == null) {
            if (isValueEmpty(nama)) {
                messages.add("nama is required");
                return messages;
            }

            if (isValueEmpty(status)) {
                messages.add("status is required");
                return messages;
            }

            if (isValueEmpty(jk)) {
                messages.add("jenis kelamin is required");
                return messages;
            }

            if (isValueEmpty(alamat)) {
                messages.add("alamat is required");
                return messages;
            }

            if (isValueEmpty(dob)) {
                messages.add("date of birth is required");
                return messages;
            }
        }

        // check nama
        if (nama != null) {
            if (nama.isBlank()) {
                messages.add("nama cannot be empty");
                return messages;
            }
            if (!nama.matches("[a-zA-Z ]+")) {
                messages.add("nama must be alphabet only");
            }
            if (nama.length() > 100) {
                messages.add("nama max 100 characters");
            }
        }

        // check status
        if (status != null) {
            if (status.isBlank()) {
                messages.add("status cannot be empty");
                return messages;
            }
            if (!status.equalsIgnoreCase("menikah")) {
                if (!status.equalsIgnoreCase("belum menikah")) {
                    messages.add("status must be 'menikah' or 'belum menikah'");
                }
            }
        }

        // check jenis kelamin (jk)
        if (jk != null) {
            if (jk.isBlank()) {
                messages.add("jenis kelamin cannot be empty");
                return messages;
            }
            if (!jk.equalsIgnoreCase("laki-laki")) {
                if (!jk.equalsIgnoreCase("perempuan")) {
                    messages.add("jenis kelamin must be 'laki-laki' or 'perempuan'");
                }
            }
        }

        // check alamat
        if (alamat != null) {
            if (alamat.isBlank()) {
                messages.add("alamat cannot be empty");
                return messages;
            }
            if (alamat.length() > 200) {
                messages.add("alamat max 200 characters");
            }
        }

        // check date of birth (dob)
        if (dob != null) {
            if (dob.isBlank()) {
                messages.add("date of birth cannot be empty");
                return messages;
            }
            try {
                SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
                sdfrmt.setLenient(false);
                sdfrmt.parse(dob);
            } catch (ParseException e) {
                messages.add("date format must be 'yyyy-MM-dd'");
            }
        }

        return messages;
    }

    public static ArrayList<String> checkCreateDetailkaryawan(DetailKaryawanData dk) {
        ArrayList<String> messages = new ArrayList<>();

        if (dk == null) {
            messages.add("detailKaryawan is required");
            return messages;
        }

        String nik = dk.getNik();
        String npwp = dk.getNpwp();

        // check nik
        if (isValueEmpty(nik)) {
            messages.add("nik cannot be empty");
            return messages;
        }
        if (!nik.matches("^\\d+$")) {
            messages.add("nik must be number");
        }
        if (nik.length() != 16) {
            messages.add("nik length must be 16");
        }

        // check npwp
        if (isValueEmpty(npwp)) {
            messages.add("npwp cannot be empty");
            return messages;
        }
        if (!npwp.matches("^\\d+$")) {
            messages.add("npwp must be number");
        }
        if (npwp.length() != 16) {
            messages.add("npwp length must be 16");
        }

        return messages;
    }

    public static ArrayList<String> checkUpdateDetailkaryawan(DetailKaryawanData dk) {
        ArrayList<String> messages = new ArrayList<>();
        String nik = dk.getNik();
        String npwp = dk.getNpwp();

        // check nik
        if (nik != null) {
            if (nik.isBlank()) {
                messages.add("nik cannot be empty");
                return messages;
            }
            if (!nik.matches("^\\d+$")) {
                messages.add("nik must be number");
            }
            if (nik.length() != 16) {
                messages.add("nik length must be 16");
            }
        }

        // check npwp
        if (npwp != null) {
            if (npwp.isBlank()) {
                messages.add("npwp cannot be empty");
                return messages;
            }
            if (!npwp.matches("^\\d+$")) {
                messages.add("npwp must be number");
            }
            if (npwp.length() != 16) {
                messages.add("npwp length must be 16");
            }
        }

        return messages;
    }

    public static ArrayList<String> checkTraining(TrainingData t) {
        ArrayList<String> messages = new ArrayList<>();
        String nama_pengajar = t.getNama_pengajar();
        String tema = t.getTema();
        String id = t.getId();

        // this condition only for CREATE
        if (id == null) {
            if (isValueEmpty(nama_pengajar)) {
                messages.add("nama pengajar is required");
                return messages;
            }
            if (isValueEmpty(tema)) {
                messages.add("tema is required");
                return messages;
            }
        }

        // check nama pengajar
        if (nama_pengajar != null) {
            if (nama_pengajar.isBlank()) {
                messages.add("nama pengajar cannot be empty");
                return messages;
            }
            if (nama_pengajar.length() > 100) {
                messages.add("nama pengajar max 100 characters");
            }
            if (!nama_pengajar.matches("[a-zA-Z ]+")) {
                messages.add("nama pengajar must be alphabet only");
            }
        }

        // check tema
        if (tema != null) {
            if (tema.isBlank()) {
                messages.add("tema cannot be empty");
                return messages;
            }
            if (tema.length() > 50) {
                messages.add("tema max 50 characters");
            }
        }

        return messages;
    }

    public static ArrayList<String> checkKaryawanTraining(KaryawanTrainingData kt) {
        ArrayList<String> messages = new ArrayList<>();
        String id = kt.getId();
        String tanggal_training = kt.getTanggal_training();
        String karyawanId = kt.getKaryawanId();
        String trainingId = kt.getTrainingId();

        // this condition only for CREATE
        if (id == null) {
            if (isValueEmpty(tanggal_training)) {
                messages.add("tanggal training is required");
                return messages;
            }
            if (isValueEmpty(karyawanId)) {
                messages.add("karyawan ID is required");
                return messages;
            }
            if (isValueEmpty(trainingId)) {
                messages.add("training ID is required");
                return messages;
            }
        }

        // check tanggal training
        if (tanggal_training != null) {
            if (tanggal_training.isBlank()) {
                messages.add("tanggal training cannot be empty");
                return messages;
            }
            try {
                SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
                sdfrmt.setLenient(false);
                sdfrmt.parse(tanggal_training);
            } catch (ParseException e) {
                messages.add("date format must be 'yyyy-MM-dd'");
            }
        }

        // check karyawan existence
        if (karyawanId != null) {
            if (karyawanId.isBlank()) {
                messages.add("karyawan ID cannot be empty");
                return messages;
            }
            if (!karyawanId.matches("^\\d+$")) {
                messages.add("karyawan id is invalid");
            }
        }

        // check training existence
        if (trainingId != null) {
            if (trainingId.isBlank()) {
                messages.add("training ID cannot be empty");
                return messages;
            }
            if (!trainingId.matches("^\\d+$")) {
                messages.add("training id is invalid");
            }
        }

        return messages;
    }

    public static ArrayList<String> checkRekening(RekeningData r) {
        ArrayList<String> messages = new ArrayList<>();
        String id = r.getId();
        String jenis = r.getJenis();
        String nama = r.getNama();
        String nomor = r.getNomor();

        // this condition only for CREATE
        if (id == null) {
            if (isValueEmpty(jenis)) {
                messages.add("jenis is required");
                return messages;
            }
            if (isValueEmpty(nama)) {
                messages.add("nama is required");
                return messages;
            }
            if (isValueEmpty(nomor)) {
                messages.add("nomor is required");
                return messages;
            }
        }

        // check jenis
        if (jenis != null) {
            if (jenis.isBlank()) {
                messages.add("jenis cannot be empty");
                return messages;
            }
            if (!jenis.matches("[a-zA-Z ]+")) {
                messages.add("jenis must be alphabet only");
            }
            if (jenis.length() > 50) {
                messages.add("jenis max 50 characters");
            }
        }

        // check nama
        if (nama != null) {
            if (nama.isBlank()) {
                messages.add("nama cannot be empty");
                return messages;
            }
            if (!nama.matches("[a-zA-Z ]+")) {
                messages.add("nama must be alphabet only");
            }
            if (nama.length() > 50) {
                messages.add("nama max 50 characters");
            }
        }

        // check nomor
        if (nomor != null) {
            if (nomor.isBlank()) {
                messages.add("nomor cannot be empty");
                return messages;
            }
            if (!nomor.matches("^\\d+$")) {
                messages.add("nomor must be number only");
            }
            if (nomor.length() > 50) {
                messages.add("nomor max 50 characters");
            }
        }

        return messages;
    }

    public static ArrayList<String> checkSearch(SearchData search) {
        ArrayList<String> messages = new ArrayList<>();
        String keyword = search.getKeyword();
        String sort = search.getSort();
        String size = search.getSize();
        String page = search.getPage();

        if (isValueEmpty(keyword)) {
            messages.add("keyword is required");
            return messages;
        }
        if (isValueEmpty(sort)) {
            messages.add("sort is required");
            return messages;
        }
        if (isValueEmpty(size)) {
            messages.add("size is required");
            return messages;
        }
        if (isValueEmpty(page)) {
            messages.add("page is required");
            return messages;
        }

        // check keyword
        if (!keyword.matches("[a-zA-Z ]+")) {
            messages.add("keyword must be alphabet only");
        }

        // check sort
        if (!sort.equalsIgnoreCase("asc")) {
            if (!sort.equalsIgnoreCase("desc")) {
                messages.add("sort must be 'asc' or 'desc'");
            }
        }

        // check size
        if (!size.matches("^\\d+$")) {
            messages.add("size must be number");
            return messages;
        }
        if (Integer.valueOf(search.getSize()) < 1) {
            messages.add("size minimum is 1");
        }

        // check page
        if (!page.matches("^\\d+$")) {
            messages.add("page must be number");
            return messages;
        }
        if (Integer.valueOf(search.getPage()) < 0) {
            messages.add("page minimum is 0");
        }

        return messages;
    }

    public static ArrayList<String> checkRegistration(RegisterRequest request) {
        ArrayList<String> messages = new ArrayList<>();
        String name = request.getName();
        String username = request.getUsername();
        String password = request.getPassword();

        if (isValueEmpty(name)) {
            messages.add("name is required");
            return messages;
        }

        if (isValueEmpty(username)) {
            messages.add("username is required");
            return messages;
        }

        if (isValueEmpty(password)) {
            messages.add("password is required");
            return messages;
        }

        if (!name.matches("[a-zA-Z ]+")) {
            messages.add("name must be alphabet only");
        }

        if (name.length() > 100) {
            messages.add("name max 100 characters");
        }

        if (username.length() > 10) {
            messages.add("username max 10 characters");
        }

        if (password.length() > 100) {
            messages.add("password max 100 characters");
        }

        return messages;
    }

    public static ArrayList<String> checkLogin(AuthenticationRequest request) {
        ArrayList<String> messages = new ArrayList<>();
        String username = request.getUsername();
        String password = request.getPassword();

        if (isValueEmpty(username)) {
            messages.add("username is required");
            return messages;
        }

        if (isValueEmpty(password)) {
            messages.add("password is required");
            return messages;
        }

        return messages;
    }
}

