package com.railway.booking.model;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
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
    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;

    @NotNull
    @Column(name = "from_time", nullable = false)
    private LocalTime fromTime;

    @NotNull
    @Column(name = "to_time", nullable = false)
    private LocalTime toTime;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "status")
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "orders_users_FK"))
    private User user;

}