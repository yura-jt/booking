package com.railway.booking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "trains")
public class Train {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "train")
    private List<Station> stations = new ArrayList<>();

    @OneToMany(mappedBy = "train")
    private List<Flight> flights = new ArrayList<>();

}