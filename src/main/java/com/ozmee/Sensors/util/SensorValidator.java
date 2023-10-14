package com.ozmee.Sensors.util;

import com.ozmee.Sensors.models.Sensor;
import com.ozmee.Sensors.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {
    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if(sensorsService.findByName(sensor.getName()).isPresent()){
            errors.rejectValue("name", "", "Имя должно быть уникальным");
        }
    }
}
