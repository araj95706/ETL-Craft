package org.example.intuitetllapplication.controllers;

import org.example.intuitetllapplication.dtos.MappingDTO;
import org.example.intuitetllapplication.services.MappingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mapping")
public class mappingController {
    ModelMapper modelMapper;
    MappingService mappingService;

    @Autowired
    public mappingController(MappingService mappingService, ModelMapper modelMapper) {
        this.mappingService = mappingService;
        this.modelMapper =modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<MappingDTO> createMapping(@RequestBody MappingDTO mappingDTO) {
        MappingDTO resultmappingDTO = mappingService.convertModelDTO(mappingService.createMapping(mappingDTO));
        return new ResponseEntity<>(resultmappingDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get/{mapping_id}")
    public ResponseEntity<MappingDTO> getMapping(@PathVariable Long mapping_id) {
        MappingDTO resultmappingDTO = mappingService.convertModelDTO(mappingService.getMapping(mapping_id));
        return new ResponseEntity<>(resultmappingDTO, HttpStatus.CREATED);
    }
}
