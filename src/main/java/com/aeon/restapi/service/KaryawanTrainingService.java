package com.aeon.restapi.service;

import com.aeon.restapi.models.entities.KaryawanTraining;
import com.aeon.restapi.models.repos.KaryawanTrainingRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class KaryawanTrainingService {
    @Autowired
    private KaryawanTrainingRepo karyawanTrainingRepo;

    public KaryawanTraining createKaryawanTraining(KaryawanTraining karyawanTraining) {
        karyawanTraining.setCreated_date(new Date());
        return karyawanTrainingRepo.save(karyawanTraining);
    }

    public KaryawanTraining updateKaryawanTraining(KaryawanTraining karyawanTraining) {
        if (karyawanTraining.getTanggal_training() == null) {
            karyawanTraining.setTanggal_training(
                    findKaryawanTraining(karyawanTraining.getId()).getTanggal_training()
            );
        }

        if (karyawanTraining.getKaryawan() == null) {
            karyawanTraining.setKaryawan(
                    findKaryawanTraining(karyawanTraining.getId()).getKaryawan()
            );
        }

        if (karyawanTraining.getTraining() == null) {
            karyawanTraining.setTraining(
                    findKaryawanTraining(karyawanTraining.getId()).getTraining()
            );
        }

        karyawanTraining.setCreated_date(
                findKaryawanTraining(karyawanTraining.getId()).getCreated_date()
        );

        karyawanTraining.setUpdated_date(new Date());

        return karyawanTrainingRepo.save(karyawanTraining);
    }

    public KaryawanTraining deleteKaryawanTraining(KaryawanTraining karyawanTraining) {
        karyawanTraining.setDeleted_date(new Date());
        return karyawanTrainingRepo.save(karyawanTraining);
    }

    public KaryawanTraining findKaryawanTraining(Long id) {
        Optional<KaryawanTraining> karyawanTraining = karyawanTrainingRepo.findById(id);
        if (!karyawanTraining.isPresent() || karyawanTraining.get().getDeleted_date() != null) {
            return null;
        }

        return karyawanTraining.get();
    }

    public Iterable<KaryawanTraining> searchKaryawanTraining(String keyword, Pageable pageable) {
        return karyawanTrainingRepo.searchKaryawanTrainingByNamaKaryawanOrTemaTraining("%" + keyword + "%", pageable);
    }
}
