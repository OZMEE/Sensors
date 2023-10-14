package com.ozmee.Sensors.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "measurements")
public class Measurement {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="value")
    @Min(value=-100, message = "Значение не можем быть меньше -100")
    @Max(value=100, message = "Значение не может превышать 100")
    private float value;

    @NotNull(message = "Поле raining не может быть пустым")
    @Column(name="raining")
    private boolean raining;

    @ManyToOne
    @JoinColumn(name="sensors_id", referencedColumnName = "id")
    private Sensor sensor;

    public Measurement(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString(){
        return "{"+value+", raining: " + raining + ", " + sensor +"} " ;
    }
}
