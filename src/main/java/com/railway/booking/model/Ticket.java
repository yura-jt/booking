package com.railway.booking.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "departure_station", nullable = false)
    private String departureStation;

    @NotNull
    @Column(name = "destination_station", nullable = false)
    private String destinationStation;

    @NotNull
    @Column(name = "passenger_name", nullable = false)
    private String passengerName;

    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "flight_id", foreignKey = @ForeignKey(name = "tickets_flight_id_FK"))
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "seat_id", foreignKey = @ForeignKey(name = "tickets_seat_id_FK"))
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "tickets_user_id_FK"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "bill_id", foreignKey = @ForeignKey(name = "tickets_bill_id_FK"))
    private Bill bill;

    @NotNull
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn = LocalDateTime.now();

}