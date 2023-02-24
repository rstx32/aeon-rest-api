package com.aeon.restapi.models.repos;

import com.aeon.restapi.models.entities.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RekeningRepo extends JpaRepository<Rekening, Long> {
    List<Rekening> findRekeningByKaryawan_Id(Long id);
}
