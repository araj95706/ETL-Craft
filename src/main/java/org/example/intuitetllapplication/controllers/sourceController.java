package org.example.intuitetllapplication.controllers;

import org.example.intuitetllapplication.dtos.SourceDTO;
import org.example.intuitetllapplication.services.SourceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/source")
public class sourceController {
    @Autowired
    ModelMapper modelMapper;
    SourceService SourceService;

    @Autowired
    public sourceController(SourceService SourceService) {
        this.SourceService = SourceService;
    }

    @PostMapping("/create")
    public ResponseEntity<SourceDTO> createSource(@RequestBody SourceDTO sourceDTO) {
        SourceDTO sourceDTOResponse = SourceService.convertModelDTO(SourceService.createSource(sourceDTO));
        return new ResponseEntity<>(sourceDTOResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get/{sourceID}")
    public ResponseEntity<SourceDTO> getSourceByID(@PathVariable Long sourceID) {
        SourceDTO sourceDTOResponse = SourceService.convertModelDTO(SourceService.getSourceByID(sourceID));
        return new ResponseEntity<>(sourceDTOResponse, HttpStatus.ACCEPTED);
    }

}
