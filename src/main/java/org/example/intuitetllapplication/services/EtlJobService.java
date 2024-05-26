package org.example.intuitetllapplication.services;

import org.example.intuitetllapplication.Builders.ValidJob;
import org.example.intuitetllapplication.Exceptions.*;
import org.example.intuitetllapplication.dtos.EtlJobRequestDTO;
import org.example.intuitetllapplication.dtos.EtlJobResponseDTO;
import org.example.intuitetllapplication.model.EtlJob;
import org.example.intuitetllapplication.model.Status;
import org.example.intuitetllapplication.repositories.EtlJobRepository;
import org.example.intuitetllapplication.util.MainJob;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EtlJobService {
    private final ModelMapper modelMapper;
    private final EtlJobRepository etlJobRepository;
    private final SourceService sourceService;
    private final MappingService mappingService;
    private final MainJob mainJob;

    @Autowired
    public EtlJobService(EtlJobRepository etlJobRepository, ModelMapper modelMapper, SourceService SourceService, MappingService mappingService, MainJob mainJob) {
        this.modelMapper = modelMapper;
        this.etlJobRepository = etlJobRepository;
        this.sourceService = SourceService;
        this.mappingService = mappingService;
        this.mainJob = mainJob;
    }

    public EtlJob createJob(EtlJobRequestDTO etlJobRequestDTO) {
        try {
            EtlJob etlJob = convertDTOtoModel(etlJobRequestDTO);
            ValidJob isValidJob = new ValidJob.JobBuilder().
                    setJob_name(etlJob.getJob_name()).
                    setJob_id(etlJob.getJob_id()).
                    setJobStatus(Status.IN_PROCESS).
                    setBatch_size(etlJob.getBatch_size()).
                    setSource(etlJob.getSource()).
                    setMapping(etlJob.getMapping()).
                    setBucket_name(etlJob.getBucket_name()).
                    setFile_name(etlJob.getFile_name()).
                    build();
            validate(isValidJob);
            EtlJob etlJobToSave = modelMapper.map(isValidJob, EtlJob.class);
            EtlJob saved = etlJobRepository.save(etlJobToSave);
            Optional<EtlJob> SavedJob = etlJobRepository.findById(saved.getId());
            // Asynchronous can be done
            run(SavedJob.get().getId());
            return SavedJob.get();
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public EtlJob run(Long job_id) {
        try {
            EtlJob etlJob = getJobByID(job_id);
            mainJob.processJob(etlJob);
            return etlJob;
        } catch (Exception e) {

        }
        return null;
    }

    private void validate(ValidJob validJob) throws MappingNotAllowedException {
        // Validations
        validateSourceMapping(validJob);
    }


    private void validateSourceMapping(ValidJob validJob) throws MappingNotAllowedException {
        if (!validJob.getSource().getAllowedMappings().contains(validJob.getMapping())) {
            throw new MappingNotAllowedException();
        }
    }

    public EtlJob convertDTOtoModel(EtlJobRequestDTO etlJobRequestDTO) throws SourceNotFoundException, MappinNotFoundException, InvalidSourceIDException, InvalidMappingIDException {
        EtlJob etlJob = new EtlJob();
        etlJob.setJob_name(etlJobRequestDTO.getJob_name());
        etlJob.setJob_id(etlJobRequestDTO.getJob_id());
        etlJob.setJobStatus(Status.IN_PROCESS);
        etlJob.setFile_name(etlJobRequestDTO.getFile_name());
        etlJob.setBucket_name(etlJobRequestDTO.getBucket_name());
        etlJob.setBatch_size(etlJobRequestDTO.getBatch_size());
        setSourceFromDTO(etlJob, etlJobRequestDTO);
        setMappingFromDTO(etlJob, etlJobRequestDTO);
        return etlJob;
    }

    public EtlJobResponseDTO convertModelDTO(EtlJob etlJob) {
        EtlJobResponseDTO etlJobResponseDTO = new EtlJobResponseDTO();
        etlJobResponseDTO.setId(etlJob.getId());
        etlJobResponseDTO.setJob_name(etlJob.getJob_name());
        etlJobResponseDTO.setJob_id(etlJob.getJob_id());
        etlJobResponseDTO.setBatch_size(etlJob.getBatch_size());
        etlJobResponseDTO.setFile_name(etlJob.getFile_name());
        etlJobResponseDTO.setBucket_name(etlJob.getBucket_name());
        etlJobResponseDTO.setCreatedAt(etlJob.getCreatedAt());
        etlJobResponseDTO.setLastModifiedAt(etlJob.getLastModifiedAt());
        etlJobResponseDTO.setMapping_id(etlJob.getMapping().getId());
        etlJobResponseDTO.setSource_id(etlJob.getSource().getId());
        return etlJobResponseDTO;
    }

    public void setSourceFromDTO(EtlJob etlJob, EtlJobRequestDTO etlJobRequestDTO) throws SourceNotFoundException, InvalidSourceIDException {
        if (etlJobRequestDTO.getSource_id() != null) {
            if (sourceService.getSourceByID(etlJobRequestDTO.getSource_id()) != null) {
                etlJob.setSource(sourceService.getSourceByID(etlJobRequestDTO.getSource_id()));
            } else {
                throw new InvalidSourceIDException();
            }
        } else if (etlJobRequestDTO.getSource() != null) {
            // source Builder
            etlJob.setSource(etlJobRequestDTO.getSource());
        } else {
            throw new SourceNotFoundException();
        }
    }

    public void setMappingFromDTO(EtlJob etlJob, EtlJobRequestDTO etlJobRequestDTO) throws MappinNotFoundException, InvalidMappingIDException {
        if (etlJobRequestDTO.getMapping_id() != null) {
            if (mappingService.getMapping(etlJobRequestDTO.getMapping_id()) != null) {
                etlJob.setMapping(mappingService.getMapping(etlJobRequestDTO.getMapping_id()));
            } else {
                throw new InvalidMappingIDException();
            }
        } else if (etlJobRequestDTO.getMapping() != null) {
            // Mapping Builder
            etlJob.setMapping(etlJobRequestDTO.getMapping());
        } else {
            throw new MappinNotFoundException();
        }
    }
    public EtlJob getJobByID(Long id) {
        try {
            Optional<EtlJob> SavedJob = etlJobRepository.findById(id);
            EtlJob etlJob = SavedJob.get();
            return etlJob;
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
