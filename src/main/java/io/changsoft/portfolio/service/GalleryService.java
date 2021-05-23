package io.changsoft.portfolio.service;

import io.changsoft.portfolio.service.dto.GalleryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link io.changsoft.portfolio.domain.Gallery}.
 */
public interface GalleryService {
    /**
     * Save a gallery.
     *
     * @param galleryDTO the entity to save.
     * @return the persisted entity.
     */
    GalleryDTO save(GalleryDTO galleryDTO);

    /**
     * Partially updates a gallery.
     *
     * @param galleryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GalleryDTO> partialUpdate(GalleryDTO galleryDTO);

    /**
     * Get all the galleries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GalleryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gallery.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GalleryDTO> findOne(Long id);

    /**
     * Delete the "id" gallery.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
