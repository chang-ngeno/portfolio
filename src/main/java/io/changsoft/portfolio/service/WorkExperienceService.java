package io.changsoft.portfolio.service;

import io.changsoft.portfolio.service.dto.WorkExperienceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link io.changsoft.portfolio.domain.WorkExperience}.
 */
public interface WorkExperienceService {
    /**
     * Save a workExperience.
     *
     * @param workExperienceDTO the entity to save.
     * @return the persisted entity.
     */
    WorkExperienceDTO save(WorkExperienceDTO workExperienceDTO);

    /**
     * Partially updates a workExperience.
     *
     * @param workExperienceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WorkExperienceDTO> partialUpdate(WorkExperienceDTO workExperienceDTO);

    /**
     * Get all the workExperiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WorkExperienceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" workExperience.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkExperienceDTO> findOne(Long id);

    /**
     * Delete the "id" workExperience.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
