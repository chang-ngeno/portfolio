package io.changsoft.portfolio.service;

import io.changsoft.portfolio.service.dto.SocialMediaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.changsoft.portfolio.domain.SocialMedia}.
 */
public interface SocialMediaService {
    /**
     * Save a socialMedia.
     *
     * @param socialMediaDTO the entity to save.
     * @return the persisted entity.
     */
    SocialMediaDTO save(SocialMediaDTO socialMediaDTO);

    /**
     * Partially updates a socialMedia.
     *
     * @param socialMediaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SocialMediaDTO> partialUpdate(SocialMediaDTO socialMediaDTO);

    /**
     * Get all the socialMedias.
     *
     * @return the list of entities.
     */
    List<SocialMediaDTO> findAll();

    /**
     * Get the "id" socialMedia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocialMediaDTO> findOne(Long id);

    /**
     * Delete the "id" socialMedia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
