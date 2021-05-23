package io.changsoft.portfolio.web.rest;

import io.changsoft.portfolio.repository.GalleryRepository;
import io.changsoft.portfolio.service.GalleryService;
import io.changsoft.portfolio.service.dto.GalleryDTO;
import io.changsoft.portfolio.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.changsoft.portfolio.domain.Gallery}.
 */
@RestController
@RequestMapping("/api")
public class GalleryResource {

    private final Logger log = LoggerFactory.getLogger(GalleryResource.class);

    private static final String ENTITY_NAME = "gallery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GalleryService galleryService;

    private final GalleryRepository galleryRepository;

    public GalleryResource(GalleryService galleryService, GalleryRepository galleryRepository) {
        this.galleryService = galleryService;
        this.galleryRepository = galleryRepository;
    }

    /**
     * {@code POST  /galleries} : Create a new gallery.
     *
     * @param galleryDTO the galleryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new galleryDTO, or with status {@code 400 (Bad Request)} if the gallery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/galleries")
    public ResponseEntity<GalleryDTO> createGallery(@Valid @RequestBody GalleryDTO galleryDTO) throws URISyntaxException {
        log.debug("REST request to save Gallery : {}", galleryDTO);
        if (galleryDTO.getId() != null) {
            throw new BadRequestAlertException("A new gallery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GalleryDTO result = galleryService.save(galleryDTO);
        return ResponseEntity
            .created(new URI("/api/galleries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /galleries/:id} : Updates an existing gallery.
     *
     * @param id the id of the galleryDTO to save.
     * @param galleryDTO the galleryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated galleryDTO,
     * or with status {@code 400 (Bad Request)} if the galleryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the galleryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/galleries/{id}")
    public ResponseEntity<GalleryDTO> updateGallery(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GalleryDTO galleryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Gallery : {}, {}", id, galleryDTO);
        if (galleryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, galleryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!galleryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GalleryDTO result = galleryService.save(galleryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, galleryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /galleries/:id} : Partial updates given fields of an existing gallery, field will ignore if it is null
     *
     * @param id the id of the galleryDTO to save.
     * @param galleryDTO the galleryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated galleryDTO,
     * or with status {@code 400 (Bad Request)} if the galleryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the galleryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the galleryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/galleries/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GalleryDTO> partialUpdateGallery(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GalleryDTO galleryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gallery partially : {}, {}", id, galleryDTO);
        if (galleryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, galleryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!galleryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GalleryDTO> result = galleryService.partialUpdate(galleryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, galleryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /galleries} : get all the galleries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of galleries in body.
     */
    @GetMapping("/galleries")
    public ResponseEntity<List<GalleryDTO>> getAllGalleries(Pageable pageable) {
        log.debug("REST request to get a page of Galleries");
        Page<GalleryDTO> page = galleryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /galleries/:id} : get the "id" gallery.
     *
     * @param id the id of the galleryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the galleryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/galleries/{id}")
    public ResponseEntity<GalleryDTO> getGallery(@PathVariable Long id) {
        log.debug("REST request to get Gallery : {}", id);
        Optional<GalleryDTO> galleryDTO = galleryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(galleryDTO);
    }

    /**
     * {@code DELETE  /galleries/:id} : delete the "id" gallery.
     *
     * @param id the id of the galleryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/galleries/{id}")
    public ResponseEntity<Void> deleteGallery(@PathVariable Long id) {
        log.debug("REST request to delete Gallery : {}", id);
        galleryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
