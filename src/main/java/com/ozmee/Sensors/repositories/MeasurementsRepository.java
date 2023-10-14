package com.ozmee.Sensors.repositories;

import com.ozmee.Sensors.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    List<Measurement> findAll();

    Optional<Measurement> findById(int id);
}
