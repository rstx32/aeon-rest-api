package com.aeon.restapi.models.repos;

import com.aeon.restapi.models.entities.Karyawan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KaryawanRepo extends JpaRepository<Karyawan, Long> {
    // search Karyawan by Nama, insensitive case
    Page<Karyawan> searchByNamaContainsIgnoreCase(String nama, Pageable pageable);
}
