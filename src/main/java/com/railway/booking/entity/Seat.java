package com.railway.booking.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer integer;

    @MapsId
    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "carriage_id", foreignKey = @ForeignKey(name = "seats_carriages_FK"))
    private Carriage carriage;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "status")
    private SeatStatus status;

    @OneToMany(mappedBy = "seat")
    private List<Ticket> seats = new ArrayList<>();

}