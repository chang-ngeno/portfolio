package io.changsoft.portfolio.service;

import io.changsoft.portfolio.domain.HireMeSubject;
import io.changsoft.portfolio.repository.HireMeSubjectRepository;
import io.changsoft.portfolio.service.dto.HireMeSubjectDTO;
import io.changsoft.portfolio.service.mapper.HireMeSubjectMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HireMeSubject}.
 */
@Service
@Transactional
public class HireMeSubjectService {

    private final Logger log = LoggerFactory.getLogger(HireMeSubjectService.class);

    private final HireMeSubjectRepository hireMeSubjectRepository;

    private final HireMeSubjectMapper hireMeSubjectMapper;

    public HireMeSubjectService(HireMeSubjectRepository hireMeSubjectRepository, HireMeSubjectMapper hireMeSubjectMapper) {
        this.hireMeSubjectRepository = hireMeSubjectRepository;
        this.hireMeSubjectMapper = hireMeSubjectMapper;
    }

    /**
     * Save a hireMeSubject.
     *
     * @param hireMeSubjectDTO the entity to save.
     * @return the persisted entity.
     */
    public HireMeSubjectDTO save(HireMeSubjectDTO hireMeSubjectDTO) {
        log.debug("Request to save HireMeSubject : {}", hireMeSubjectDTO);
        HireMeSubject hireMeSubject = hireMeSubjectMapper.toEntity(hireMeSubjectDTO);
        hireMeSubject = hireMeSubjectRepository.save(hireMeSubject);
        return hireMeSubjectMapper.toDto(hireMeSubject);
    }

    /**
     * Partially update a hireMeSubject.
     *
     * @param hireMeSubjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HireMeSubjectDTO> partialUpdate(HireMeSubjectDTO hireMeSubjectDTO) {
        log.debug("Request to partially update HireMeSubject : {}", hireMeSubjectDTO);

        return hireMeSubjectRepository
            .findById(hireMeSubjectDTO.getId())
            .map(
                existingHireMeSubject -> {
                    hireMeSubjectMapper.partialUpdate(existingHireMeSubject, hireMeSubjectDTO);
                    return existingHireMeSubject;
                }
            )
            .map(hireMeSubjectRepository::save)
            .map(hireMeSubjectMapper::toDto);
    }

    /**
     * Get all the hireMeSubjects.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<HireMeSubjectDTO> findAll() {
        log.debug("Request to get all HireMeSubjects");
        return hireMeSubjectRepository.findAll().stream().map(hireMeSubjectMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one hireMeSubject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HireMeSubjectDTO> findOne(Long id) {
        log.debug("Request to get HireMeSubject : {}", id);
        return hireMeSubjectRepository.findById(id).map(hireMeSubjectMapper::toDto);
    }

    /**
     * Delete the hireMeSubject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HireMeSubject : {}", id);
        hireMeSubjectRepository.deleteById(id);
    }
}
