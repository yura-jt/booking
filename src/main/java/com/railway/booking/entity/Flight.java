package com.railway.booking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;

    @ManyToOne
    @JoinColumn(name = "train_id", foreignKey = @ForeignKey(name = "flights_trains_FK"))
    private Train train;

    @OneToMany(mappedBy = "flight")
    private List<Ticket> flights = new ArrayList<>();

    @OneToMany(mappedBy = "flight")
    private List<Ticket> carriages = new ArrayList<>();

}