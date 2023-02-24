package com.aeon.restapi.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "detail_karyawan")
@Setter
@Getter
public class DetailKaryawan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nik;

    private String npwp;

    @OneToOne
    @JoinColumn(name = "karyawan_id")
    private Karyawan karyawan;
}
