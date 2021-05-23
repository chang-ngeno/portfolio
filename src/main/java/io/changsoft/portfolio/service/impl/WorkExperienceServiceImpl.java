package io.changsoft.portfolio.service.impl;

import io.changsoft.portfolio.domain.WorkExperience;
import io.changsoft.portfolio.repository.WorkExperienceRepository;
import io.changsoft.portfolio.service.WorkExperienceService;
import io.changsoft.portfolio.service.dto.WorkExperienceDTO;
import io.changsoft.portfolio.service.mapper.WorkExperienceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkExperience}.
 */
@Service
@Transactional
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final Logger log = LoggerFactory.getLogger(WorkExperienceServiceImpl.class);

    private final WorkExperienceRepository workExperienceRepository;

    private final WorkExperienceMapper workExperienceMapper;

    public WorkExperienceServiceImpl(WorkExperienceRepository workExperienceRepository, WorkExperienceMapper workExperienceMapper) {
        this.workExperienceRepository = workExperienceRepository;
        this.workExperienceMapper = workExperienceMapper;
    }

    @Override
    public WorkExperienceDTO save(WorkExperienceDTO workExperienceDTO) {
        log.debug("Request to save WorkExperience : {}", workExperienceDTO);
        WorkExperience workExperience = workExperienceMapper.toEntity(workExperienceDTO);
        workExperience = workExperienceRepository.save(workExperience);
        return workExperienceMapper.toDto(workExperience);
    }

    @Override
    public Optional<WorkExperienceDTO> partialUpdate(WorkExperienceDTO workExperienceDTO) {
        log.debug("Request to partially update WorkExperience : {}", workExperienceDTO);

        return workExperienceRepository
            .findById(workExperienceDTO.getId())
            .map(
                existingWorkExperience -> {
                    workExperienceMapper.partialUpdate(existingWorkExperience, workExperienceDTO);
                    return existingWorkExperience;
                }
            )
            .map(workExperienceRepository::save)
            .map(workExperienceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkExperienceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkExperiences");
        return workExperienceRepository.findAll(pageable).map(workExperienceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkExperienceDTO> findOne(Long id) {
        log.debug("Request to get WorkExperience : {}", id);
        return workExperienceRepository.findById(id).map(workExperienceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkExperience : {}", id);
        workExperienceRepository.deleteById(id);
    }
}
