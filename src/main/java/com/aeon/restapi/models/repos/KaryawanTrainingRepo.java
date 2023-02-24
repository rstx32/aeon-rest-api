package com.aeon.restapi.models.repos;

import com.aeon.restapi.models.entities.KaryawanTraining;

import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface KaryawanTrainingRepo extends CrudRepository<KaryawanTraining, Long> {

    @Query("SELECT kt FROM KaryawanTraining kt " +
            "INNER JOIN Karyawan k ON k.id = kt.karyawan.id " +
            "INNER JOIN Training t ON t.id = kt.training.id " +
            "WHERE LOWER(k.nama) LIKE :keyword " +
            "OR LOWER(t.tema) LIKE :keyword")
    Page<KaryawanTraining> searchKaryawanTrainingByNamaKaryawanOrTemaTraining(@PathParam("keyword") String keyword, Pageable pageable);
}
