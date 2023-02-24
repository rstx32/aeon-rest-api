package com.aeon.restapi.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "karyawan")
@Setter
@Getter
public class Karyawan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nama;

    @Column(length = 9)
    private String jk;

    @Column(length = 13)
    private String status;

    @Column(length = 200)
    private String alamat;

    @Column(columnDefinition = "DATE")
    private LocalDate dob;

    @Column(columnDefinition = "TIMESTAMP")
    private Date created_date;

    @Column(columnDefinition = "TIMESTAMP")
    private Date deleted_date;

    @Column(columnDefinition = "TIMESTAMP")
    private Date updated_date;

//    @OneToOne(mappedBy = "karyawan", cascade=CascadeType.ALL)
//    @JsonIgnoreProperties("id")
//    private DetailKaryawan detailKaryawan;
//
//    @OneToMany(mappedBy = "karyawan", cascade=CascadeType.ALL)
//    @JsonBackReference
//    private List<KaryawanTraining> karyawanTrainingList;
//
//    @OneToMany(mappedBy = "karyawan", cascade=CascadeType.ALL)
//    @JsonBackReference
//    private List<Rekening> rekening;
}
