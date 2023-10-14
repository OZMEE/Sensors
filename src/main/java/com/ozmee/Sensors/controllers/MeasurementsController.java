package com.ozmee.Sensors.controllers;

import com.ozmee.Sensors.dto.MeasurementDTO;
import com.ozmee.Sensors.models.Measurement;
import com.ozmee.Sensors.services.MeasurementsService;
import com.ozmee.Sensors.util.MeasurementValidator;
import com.ozmee.Sensors.util.exc.MeasurementErrorResponse;
import com.ozmee.Sensors.util.exc.MeasurementNotAddedException;
import jakarta.validation.Valid;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }
    @GetMapping
    public List<MeasurementDTO> getMeasurements(){
        return measurementsService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDays(){
        return measurementsService.findAll().stream().filter(measurement -> measurement.isRaining()).count();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementValidator.validate(measurement, bindingResult);
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder str = new StringBuilder();

            for(FieldError error : errors){
                str.append(error.getField()).append(" - ").append(error.getDefaultMessage());
            }
            throw new MeasurementNotAddedException(str.toString());
        }


        measurementsService.save(measurement);
        System.out.println(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddedException measurementNotAddedException){
        MeasurementErrorResponse response = new MeasurementErrorResponse(measurementNotAddedException.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

}
