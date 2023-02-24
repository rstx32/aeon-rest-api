package com.aeon.restapi.models.repos;

import com.aeon.restapi.models.entities.DetailKaryawan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DetailKaryawanRepo extends JpaRepository<DetailKaryawan, Long> {
    Optional<DetailKaryawan> findDetailKaryawanByKaryawanId(Long karyawanId);
}
