package com.aeon.restapi.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "rekening")
@Setter
@Getter
public class Rekening implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String jenis;

    @Column(length = 50)
    private String nama;

    @Column(length = 50)
    private String nomor;

    @Column(columnDefinition = "TIMESTAMP")
    private Date created_date;

    @Column(columnDefinition = "TIMESTAMP")
    private Date deleted_date;

    @Column(columnDefinition = "TIMESTAMP")
    private Date updated_date;

    @ManyToOne
    @JoinColumn(name = "karyawan_id")
    private Karyawan karyawan;
}
