package io.changsoft.portfolio.service;

import io.changsoft.portfolio.domain.Hobby;
import io.changsoft.portfolio.repository.HobbyRepository;
import io.changsoft.portfolio.service.dto.HobbyDTO;
import io.changsoft.portfolio.service.mapper.HobbyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Hobby}.
 */
@Service
@Transactional
public class HobbyService {

    private final Logger log = LoggerFactory.getLogger(HobbyService.class);

    private final HobbyRepository hobbyRepository;

    private final HobbyMapper hobbyMapper;

    public HobbyService(HobbyRepository hobbyRepository, HobbyMapper hobbyMapper) {
        this.hobbyRepository = hobbyRepository;
        this.hobbyMapper = hobbyMapper;
    }

    /**
     * Save a hobby.
     *
     * @param hobbyDTO the entity to save.
     * @return the persisted entity.
     */
    public HobbyDTO save(HobbyDTO hobbyDTO) {
        log.debug("Request to save Hobby : {}", hobbyDTO);
        Hobby hobby = hobbyMapper.toEntity(hobbyDTO);
        hobby = hobbyRepository.save(hobby);
        return hobbyMapper.toDto(hobby);
    }

    /**
     * Partially update a hobby.
     *
     * @param hobbyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HobbyDTO> partialUpdate(HobbyDTO hobbyDTO) {
        log.debug("Request to partially update Hobby : {}", hobbyDTO);

        return hobbyRepository
            .findById(hobbyDTO.getId())
            .map(
                existingHobby -> {
                    hobbyMapper.partialUpdate(existingHobby, hobbyDTO);
                    return existingHobby;
                }
            )
            .map(hobbyRepository::save)
            .map(hobbyMapper::toDto);
    }

    /**
     * Get all the hobbies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HobbyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hobbies");
        return hobbyRepository.findAll(pageable).map(hobbyMapper::toDto);
    }

    /**
     * Get one hobby by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HobbyDTO> findOne(Long id) {
        log.debug("Request to get Hobby : {}", id);
        return hobbyRepository.findById(id).map(hobbyMapper::toDto);
    }

    /**
     * Delete the hobby by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hobby : {}", id);
        hobbyRepository.deleteById(id);
    }
}
