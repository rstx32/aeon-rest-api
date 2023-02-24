package com.aeon.restapi.service;

import com.aeon.restapi.models.entities.Rekening;
import com.aeon.restapi.models.repos.RekeningRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RekeningService {
    @Autowired
    private RekeningRepo rekeningRepo;

    public Rekening createRekening(Rekening rekening) {
        rekening.setCreated_date(new Date());
        return rekeningRepo.save(rekening);
    }

    public Rekening updateRekening(Rekening rekening) {
        if (rekening.getNama() == null) {
            rekening.setNama(
                    findRekening(rekening.getId()).getNama()
            );
        }

        if (rekening.getJenis() == null) {
            rekening.setJenis(
                    findRekening(rekening.getId()).getJenis()
            );
        }

        if (rekening.getNomor() == null) {
            rekening.setNomor(
                    findRekening(rekening.getId()).getNomor()
            );
        }

        rekening.setCreated_date(
                findRekening(rekening.getId()).getCreated_date()
        );

        rekening.setUpdated_date(new Date());

        return rekeningRepo.save(rekening);
    }

    public Rekening deleteRekening(Rekening rekening) {
        rekening.setDeleted_date(new Date());
        return rekeningRepo.save(rekening);
    }

    public Rekening findRekening(Long id) {
        Optional<Rekening> rekening = rekeningRepo.findById(id);
        if (!rekening.isPresent() || rekening.get().getDeleted_date() != null) {
            return null;
        }

        return rekening.get();
    }

    public List<Rekening> getAllRekening(Long id) {
        return rekeningRepo.findRekeningByKaryawan_Id(id);
    }
}
