package com.ozmee.Sensors.repositories;

import com.ozmee.Sensors.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    List<Sensor> findAll();

    Optional<Sensor> findById(int id);

    Optional<Sensor> findByName(String name);
}
