package org.example.intuitetllapplication.controllers;

import org.example.intuitetllapplication.dtos.EtlJobRequestDTO;
import org.example.intuitetllapplication.dtos.EtlJobResponseDTO;
import org.example.intuitetllapplication.services.EtlJobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ETLJob")
public class EtlJobController {
    ModelMapper modelMapper;
    EtlJobService etlJobService;

    @Autowired
    public EtlJobController(EtlJobService EtlJobService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.etlJobService = EtlJobService;
    }

    @PostMapping("/create")
    public ResponseEntity<EtlJobResponseDTO> createJob(@RequestBody EtlJobRequestDTO etlJobRequestDTO) {
        /*
        1. try catch
         */
        EtlJobResponseDTO etlJobResponse = etlJobService.convertModelDTO(etlJobService.createJob(etlJobRequestDTO));
        return new ResponseEntity<>(etlJobResponse, HttpStatus.CREATED);
    }

    @GetMapping("{job_id}")
    public ResponseEntity<EtlJobResponseDTO> getJob(@PathVariable Long job_id) {
        EtlJobResponseDTO etlJobResponse = etlJobService.convertModelDTO(etlJobService.getJobByID(job_id));
        return new ResponseEntity<>(etlJobResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/run/{job_id}")
    public ResponseEntity<EtlJobResponseDTO> run(@PathVariable Long job_id) {
        EtlJobResponseDTO etlJobResponse = etlJobService.convertModelDTO(etlJobService.run(job_id));
        return new ResponseEntity<>(etlJobResponse, HttpStatus.CREATED);
    }
}
