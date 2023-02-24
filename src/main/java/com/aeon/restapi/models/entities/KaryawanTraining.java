package com.aeon.restapi.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "karyawan_training")
@Setter
@Getter
public class KaryawanTraining implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE")
    private LocalDate tanggal_training;

    @Column(columnDefinition = "TIMESTAMP")
    private Date created_date;

    @Column(columnDefinition = "TIMESTAMP")
    private Date deleted_date;

    @Column(columnDefinition = "TIMESTAMP")
    private Date updated_date;

    @ManyToOne
    @JoinColumn(name = "karyawan_id")
    private Karyawan karyawan;

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training;
}
