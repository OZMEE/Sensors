package com.ozmee.Sensors.util;

import com.ozmee.Sensors.models.Measurement;
import com.ozmee.Sensors.services.SensorsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class MeasurementValidator implements Validator {
    private final SensorsService sensorsService;

    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if(sensorsService.findByName(measurement.getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor", "", "Сенсора с таким названием нет");
        }
    }
}
