package com.ozmee.Sensors.services;

import com.ozmee.Sensors.models.Measurement;
import com.ozmee.Sensors.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public Measurement findById(int id){
        return measurementsRepository.findById(id).orElse(null);
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement){
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }

    private void enrichMeasurement(Measurement measurement){
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
    }
}
