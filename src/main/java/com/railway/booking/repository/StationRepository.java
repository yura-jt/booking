package com.railway.booking.repository;

import com.railway.booking.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends JpaRepository<Station, Integer> {
    List<Station> findAllStationsByTrainId(Integer id);
}
