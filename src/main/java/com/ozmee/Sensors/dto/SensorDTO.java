package com.ozmee.Sensors.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
public class SensorDTO {

    @Size(min = 3, max = 30, message="Название должно быть от 3 до 30 символов")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
