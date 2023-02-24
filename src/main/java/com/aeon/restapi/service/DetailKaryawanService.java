package com.aeon.restapi.service;

import com.aeon.restapi.models.entities.DetailKaryawan;
import com.aeon.restapi.models.repos.DetailKaryawanRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class DetailKaryawanService {
    @Autowired
    private DetailKaryawanRepo detailKaryawanRepo;

    public DetailKaryawan createDetailKaryawan(DetailKaryawan detailKaryawan) {
        return detailKaryawanRepo.save(detailKaryawan);
    }

    public DetailKaryawan updateDetailkaryawan(DetailKaryawan detailKaryawan){
        if(detailKaryawan.getNik() == null){
            detailKaryawan.setNik(
                    findDetailKaryawan(detailKaryawan.getId()).getNik()
            );
        }

        if(detailKaryawan.getNpwp() == null){
            detailKaryawan.setNpwp(
                    findDetailKaryawan(detailKaryawan.getId()).getNpwp()
            );
        }

        return detailKaryawanRepo.save(detailKaryawan);
    }

    public DetailKaryawan findDetailKaryawan(Long id) {
        Optional<DetailKaryawan> detailKaryawan = detailKaryawanRepo.findById(id);
        if (!detailKaryawan.isPresent()) {
            return null;
        }

        return detailKaryawan.get();
    }

    public DetailKaryawan findDetailKaryawanByKaryawanID(Long id){
        Optional<DetailKaryawan> detailKaryawan = detailKaryawanRepo.findDetailKaryawanByKaryawanId(id);
        if (!detailKaryawan.isPresent()) {
            return null;
        }

        return detailKaryawan.get();
    }
}
