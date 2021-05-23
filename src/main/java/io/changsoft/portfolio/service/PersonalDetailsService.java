package io.changsoft.portfolio.service;

import io.changsoft.portfolio.domain.PersonalDetails;
import io.changsoft.portfolio.repository.PersonalDetailsRepository;
import io.changsoft.portfolio.service.dto.PersonalDetailsDTO;
import io.changsoft.portfolio.service.mapper.PersonalDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonalDetails}.
 */
@Service
@Transactional
public class PersonalDetailsService {

    private final Logger log = LoggerFactory.getLogger(PersonalDetailsService.class);

    private final PersonalDetailsRepository personalDetailsRepository;

    private final PersonalDetailsMapper personalDetailsMapper;

    public PersonalDetailsService(PersonalDetailsRepository personalDetailsRepository, PersonalDetailsMapper personalDetailsMapper) {
        this.personalDetailsRepository = personalDetailsRepository;
        this.personalDetailsMapper = personalDetailsMapper;
    }

    /**
     * Save a personalDetails.
     *
     * @param personalDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonalDetailsDTO save(PersonalDetailsDTO personalDetailsDTO) {
        log.debug("Request to save PersonalDetails : {}", personalDetailsDTO);
        PersonalDetails personalDetails = personalDetailsMapper.toEntity(personalDetailsDTO);
        personalDetails = personalDetailsRepository.save(personalDetails);
        return personalDetailsMapper.toDto(personalDetails);
    }

    /**
     * Partially update a personalDetails.
     *
     * @param personalDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PersonalDetailsDTO> partialUpdate(PersonalDetailsDTO personalDetailsDTO) {
        log.debug("Request to partially update PersonalDetails : {}", personalDetailsDTO);

        return personalDetailsRepository
            .findById(personalDetailsDTO.getId())
            .map(
                existingPersonalDetails -> {
                    personalDetailsMapper.partialUpdate(existingPersonalDetails, personalDetailsDTO);
                    return existingPersonalDetails;
                }
            )
            .map(personalDetailsRepository::save)
            .map(personalDetailsMapper::toDto);
    }

    /**
     * Get all the personalDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PersonalDetailsDTO> findAll() {
        log.debug("Request to get all PersonalDetails");
        return personalDetailsRepository
            .findAll()
            .stream()
            .map(personalDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one personalDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonalDetailsDTO> findOne(Long id) {
        log.debug("Request to get PersonalDetails : {}", id);
        return personalDetailsRepository.findById(id).map(personalDetailsMapper::toDto);
    }

    /**
     * Delete the personalDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonalDetails : {}", id);
        personalDetailsRepository.deleteById(id);
    }
}
