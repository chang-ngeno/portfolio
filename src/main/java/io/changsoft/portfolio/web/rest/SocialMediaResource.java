package io.changsoft.portfolio.web.rest;

import io.changsoft.portfolio.repository.SocialMediaRepository;
import io.changsoft.portfolio.service.SocialMediaService;
import io.changsoft.portfolio.service.dto.SocialMediaDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.changsoft.portfolio.domain.SocialMedia}.
 */
@RestController
@RequestMapping("/api")
public class SocialMediaResource {

    private final Logger log = LoggerFactory.getLogger(SocialMediaResource.class);

    private static final String ENTITY_NAME = "socialMedia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocialMediaService socialMediaService;

    private final SocialMediaRepository socialMediaRepository;

    public SocialMediaResource(SocialMediaService socialMediaService, SocialMediaRepository socialMediaRepository) {
        this.socialMediaService = socialMediaService;
        this.socialMediaRepository = socialMediaRepository;
    }

    /**
     * {@code POST  /social-medias} : Create a new socialMedia.
     *
     * @param socialMediaDTO the socialMediaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socialMediaDTO, or with status {@code 400 (Bad Request)} if the socialMedia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/social-medias")
    public ResponseEntity<SocialMediaDTO> createSocialMedia(@Valid @RequestBody SocialMediaDTO socialMediaDTO) throws URISyntaxException {
        log.debug("REST request to save SocialMedia : {}", socialMediaDTO);
        if (socialMediaDTO.getId() != null) {
            throw new BadRequestAlertException("A new socialMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialMediaDTO result = socialMediaService.save(socialMediaDTO);
        return ResponseEntity
            .created(new URI("/api/social-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /social-medias/:id} : Updates an existing socialMedia.
     *
     * @param id the id of the socialMediaDTO to save.
     * @param socialMediaDTO the socialMediaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialMediaDTO,
     * or with status {@code 400 (Bad Request)} if the socialMediaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socialMediaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/social-medias/{id}")
    public ResponseEntity<SocialMediaDTO> updateSocialMedia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SocialMediaDTO socialMediaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocialMedia : {}, {}", id, socialMediaDTO);
        if (socialMediaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialMediaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialMediaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocialMediaDTO result = socialMediaService.save(socialMediaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socialMediaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /social-medias/:id} : Partial updates given fields of an existing socialMedia, field will ignore if it is null
     *
     * @param id the id of the socialMediaDTO to save.
     * @param socialMediaDTO the socialMediaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialMediaDTO,
     * or with status {@code 400 (Bad Request)} if the socialMediaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the socialMediaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the socialMediaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/social-medias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<SocialMediaDTO> partialUpdateSocialMedia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SocialMediaDTO socialMediaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocialMedia partially : {}, {}", id, socialMediaDTO);
        if (socialMediaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialMediaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialMediaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocialMediaDTO> result = socialMediaService.partialUpdate(socialMediaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socialMediaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /social-medias} : get all the socialMedias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socialMedias in body.
     */
    @GetMapping("/social-medias")
    public List<SocialMediaDTO> getAllSocialMedias() {
        log.debug("REST request to get all SocialMedias");
        return socialMediaService.findAll();
    }

    /**
     * {@code GET  /social-medias/:id} : get the "id" socialMedia.
     *
     * @param id the id of the socialMediaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socialMediaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/social-medias/{id}")
    public ResponseEntity<SocialMediaDTO> getSocialMedia(@PathVariable Long id) {
        log.debug("REST request to get SocialMedia : {}", id);
        Optional<SocialMediaDTO> socialMediaDTO = socialMediaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialMediaDTO);
    }

    /**
     * {@code DELETE  /social-medias/:id} : delete the "id" socialMedia.
     *
     * @param id the id of the socialMediaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/social-medias/{id}")
    public ResponseEntity<Void> deleteSocialMedia(@PathVariable Long id) {
        log.debug("REST request to delete SocialMedia : {}", id);
        socialMediaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
