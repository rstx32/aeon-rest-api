package com.aeon.restapi.service;

import com.aeon.restapi.models.entities.Karyawan;
import com.aeon.restapi.models.repos.KaryawanRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class KaryawanService {
    @Autowired
    private KaryawanRepo karyawanRepo;

    public Karyawan createKaryawan(Karyawan karyawan) {
        karyawan.setCreated_date(new Date());
        return karyawanRepo.save(karyawan);
    }

    public Karyawan updateKaryawan(Karyawan karyawan) {
        if (karyawan.getNama() == null) {
            karyawan.setNama(
                    findKaryawan(karyawan.getId()).getNama()
            );
        }

        if (karyawan.getJk() == null) {
            karyawan.setJk(
                    findKaryawan(karyawan.getId()).getJk()
            );
        }

        if (karyawan.getStatus() == null) {
            karyawan.setStatus(
                    findKaryawan(karyawan.getId()).getStatus()
            );
        }

        if (karyawan.getAlamat() == null) {
            karyawan.setAlamat(
                    findKaryawan(karyawan.getId()).getAlamat()
            );
        }

        if (karyawan.getDob() == null) {
            karyawan.setDob(
                    findKaryawan(karyawan.getId()).getDob()
            );
        }

        karyawan.setCreated_date(
                findKaryawan(karyawan.getId()).getCreated_date()
        );

        karyawan.setUpdated_date(new Date());

        return karyawanRepo.save(karyawan);
    }

    public Karyawan deleteKaryawan(Karyawan karyawan) {
        karyawan.setDeleted_date(new Date());
        return karyawanRepo.save(karyawan);
    }

    public Karyawan findKaryawan(Long id) {
        Optional<Karyawan> karyawan = karyawanRepo.findById(id);
        if (!karyawan.isPresent() || karyawan.get().getDeleted_date() != null) {
            return null;
        }

        return karyawan.get();
    }

    public Iterable<Karyawan> searchKaryawan(String nama, Pageable pageable) {
        return karyawanRepo.searchByNamaContainsIgnoreCase(nama, pageable);
    }
}
