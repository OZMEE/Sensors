package com.ozmee.Sensors.controllers;

import com.ozmee.Sensors.dto.SensorDTO;
import com.ozmee.Sensors.models.Sensor;
import com.ozmee.Sensors.services.SensorsService;
import com.ozmee.Sensors.util.SensorValidator;
import com.ozmee.Sensors.util.exc.SensorErrorResponse;
import com.ozmee.Sensors.util.exc.SensorNotCreatedException;
import com.ozmee.Sensors.util.exc.SensorNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final ModelMapper modelMapper;
    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(ModelMapper modelMapper, SensorsService sensorsService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) throws Exception {
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder msg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for(FieldError error : errors){
                msg.append(error.getField()).append(" - ").append(error.getDefaultMessage());
            }
            throw new SensorNotCreatedException(msg.toString());
        }

        sensorsService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping()
    public List<Sensor> listSensor(){
        List<Sensor> list = sensorsService.findAll();
        System.out.println(list);
        return list;
    }

    @GetMapping("/{id}")
    public Sensor pageSensor(@PathVariable int id){
        return sensorsService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e){
        SensorErrorResponse response = new SensorErrorResponse("Сенсор с таким названием не найден",
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e){
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}