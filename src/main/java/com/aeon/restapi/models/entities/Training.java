package com.aeon.restapi.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "training")
@Setter
@Getter
public class Training implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nama_pengajar;

    @Column(length = 50)
    private String tema;

    @Column(columnDefinition = "TIMESTAMP")
    private Date created_date;

    @Column(columnDefinition = "TIMESTAMP")
    private Date deleted_date;

    @Column(columnDefinition = "TIMESTAMP")
    private Date updated_date;

}
