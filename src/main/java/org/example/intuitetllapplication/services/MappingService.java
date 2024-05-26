package org.example.intuitetllapplication.services;

import org.example.intuitetllapplication.dtos.MappingDTO;
import org.example.intuitetllapplication.model.Mapping;
import org.example.intuitetllapplication.repositories.MappingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MappingService {
    @Autowired
    ModelMapper modelMapper;
    MappingRepository mappingRepository;

    @Autowired
    public MappingService(MappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    public Mapping createMapping(MappingDTO mappingDTO) {
        // Validate Mapping
        Mapping mapping = convertDTOtoModel(mappingDTO);
        Mapping savedMapping = mappingRepository.save(mapping);
        Optional<Mapping> SavedMapping = mappingRepository.findById(savedMapping.getId());
        return SavedMapping.get();
    }

    public Mapping getMapping(Long mappingID) {
        Optional<Mapping> mapping = mappingRepository.findById(mappingID);
        return mapping.get();
    }
    public Mapping convertDTOtoModel(MappingDTO mappingDTO){
        Mapping mapping = new Mapping();
        mapping.setId(mappingDTO.getId());
        mapping.setMapping_name(mappingDTO.getMapping_name());
        mapping.setColumnsOrder(mappingDTO.getColumnsOrder());
        return mapping;
    }

    public MappingDTO convertModelDTO(Mapping mapping){
        MappingDTO mappingDTO = new MappingDTO();
        mappingDTO.setId(mapping.getId());
        mappingDTO.setMapping_name(mapping.getMapping_name());
        mappingDTO.setColumnsOrder(mapping.getColumnsOrder());
        mappingDTO.setCreatedAt(mapping.getCreatedAt());
        mappingDTO.setLastModifiedAt(mapping.getLastModifiedAt());
        return mappingDTO;
    }
}
