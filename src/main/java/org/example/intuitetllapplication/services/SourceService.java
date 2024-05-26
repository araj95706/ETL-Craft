package org.example.intuitetllapplication.services;

import org.example.intuitetllapplication.dtos.SourceDTO;
import org.example.intuitetllapplication.model.Mapping;
import org.example.intuitetllapplication.model.Source;
import org.example.intuitetllapplication.model.SourceType;
import org.example.intuitetllapplication.repositories.SourceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class SourceService {
    private ModelMapper modelMapper;
    private SourceRepository SourceRepository;
    MappingService mappingService;

    @Autowired
    public SourceService(SourceRepository SourceRepository, ModelMapper modelMapper, MappingService mappingService) {
        this.modelMapper = modelMapper;
        this.SourceRepository = SourceRepository;
        this.mappingService = mappingService;
    }

    public Source createSource(SourceDTO sourceDTO) {
        // Validate Source Builder
        Source source = convertDTOtoModel(sourceDTO);
        SourceRepository.save(source);
        Optional<Source> SavedSource = SourceRepository.findById(source.getId());
        return SavedSource.get();
    }

    public Source getSourceByID(Long sourceID) {
        Optional<Source> SavedSource = SourceRepository.findById(sourceID);
        return SavedSource.get();
    }
    public Source convertDTOtoModel(SourceDTO sourceDTO){
        Source source = new Source();
        source.setId(sourceDTO.getId());
        source.setName(sourceDTO.getName());
        source.setSource_path(sourceDTO.getSource_path());
        source.setSourceType(SourceType.fromInteger(sourceDTO.getSource_type()));
        source.setFileName(sourceDTO.getFileName());
        source.setBucketName(sourceDTO.getBucketName());
        for (Long mappingId : sourceDTO.getAllowedMappings()){
            source.getAllowedMappings().add(mappingService.getMapping(mappingId));
        }
        return source;
    }

    public SourceDTO convertModelDTO(Source source){
        SourceDTO sourceDTO = new SourceDTO();
        sourceDTO.setSource_type(source.getSourceType().ordinal());
        sourceDTO.setId(source.getId());
        sourceDTO.setName(source.getName());
        sourceDTO.setSource_path(source.getSource_path());
        sourceDTO.setFileName(source.getFileName());
        sourceDTO.setBucketName(source.getBucketName());
        sourceDTO.setCreatedAt(source.getCreatedAt());
        sourceDTO.setLastModifiedAt(source.getLastModifiedAt());
        for(Mapping mapping: source.getAllowedMappings()){
            sourceDTO.getAllowedMappings().add(mapping.getId());
        }
        return sourceDTO;
    }
}
