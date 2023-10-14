package com.ozmee.Sensors.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ozmee.Sensors.models.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class MeasurementDTO {
    @Min(value=-100, message = "Значение не можем быть меньше -100")
    @Max(value=100, message = "Значение не может превышать 100")
    private float value;

    //@NotEmpty(message = "Поле raining не может быть пустым")
    @NotNull
    private boolean raining;

    private Sensor sensor;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
