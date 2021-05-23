package io.changsoft.portfolio.web.rest;

import io.changsoft.portfolio.repository.HireMeSubjectRepository;
import io.changsoft.portfolio.service.HireMeSubjectService;
import io.changsoft.portfolio.service.dto.HireMeSubjectDTO;
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
 * REST controller for managing {@link io.changsoft.portfolio.domain.HireMeSubject}.
 */
@RestController
@RequestMapping("/api")
public class HireMeSubjectResource {

    private final Logger log = LoggerFactory.getLogger(HireMeSubjectResource.class);

    private static final String ENTITY_NAME = "hireMeSubject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HireMeSubjectService hireMeSubjectService;

    private final HireMeSubjectRepository hireMeSubjectRepository;

    public HireMeSubjectResource(HireMeSubjectService hireMeSubjectService, HireMeSubjectRepository hireMeSubjectRepository) {
        this.hireMeSubjectService = hireMeSubjectService;
        this.hireMeSubjectRepository = hireMeSubjectRepository;
    }

    /**
     * {@code POST  /hire-me-subjects} : Create a new hireMeSubject.
     *
     * @param hireMeSubjectDTO the hireMeSubjectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hireMeSubjectDTO, or with status {@code 400 (Bad Request)} if the hireMeSubject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hire-me-subjects")
    public ResponseEntity<HireMeSubjectDTO> createHireMeSubject(@Valid @RequestBody HireMeSubjectDTO hireMeSubjectDTO)
        throws URISyntaxException {
        log.debug("REST request to save HireMeSubject : {}", hireMeSubjectDTO);
        if (hireMeSubjectDTO.getId() != null) {
            throw new BadRequestAlertException("A new hireMeSubject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HireMeSubjectDTO result = hireMeSubjectService.save(hireMeSubjectDTO);
        return ResponseEntity
            .created(new URI("/api/hire-me-subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hire-me-subjects/:id} : Updates an existing hireMeSubject.
     *
     * @param id the id of the hireMeSubjectDTO to save.
     * @param hireMeSubjectDTO the hireMeSubjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hireMeSubjectDTO,
     * or with status {@code 400 (Bad Request)} if the hireMeSubjectDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hireMeSubjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hire-me-subjects/{id}")
    public ResponseEntity<HireMeSubjectDTO> updateHireMeSubject(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HireMeSubjectDTO hireMeSubjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HireMeSubject : {}, {}", id, hireMeSubjectDTO);
        if (hireMeSubjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hireMeSubjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hireMeSubjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HireMeSubjectDTO result = hireMeSubjectService.save(hireMeSubjectDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hireMeSubjectDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hire-me-subjects/:id} : Partial updates given fields of an existing hireMeSubject, field will ignore if it is null
     *
     * @param id the id of the hireMeSubjectDTO to save.
     * @param hireMeSubjectDTO the hireMeSubjectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hireMeSubjectDTO,
     * or with status {@code 400 (Bad Request)} if the hireMeSubjectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hireMeSubjectDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hireMeSubjectDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hire-me-subjects/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HireMeSubjectDTO> partialUpdateHireMeSubject(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HireMeSubjectDTO hireMeSubjectDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HireMeSubject partially : {}, {}", id, hireMeSubjectDTO);
        if (hireMeSubjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hireMeSubjectDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hireMeSubjectRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HireMeSubjectDTO> result = hireMeSubjectService.partialUpdate(hireMeSubjectDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hireMeSubjectDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hire-me-subjects} : get all the hireMeSubjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hireMeSubjects in body.
     */
    @GetMapping("/hire-me-subjects")
    public List<HireMeSubjectDTO> getAllHireMeSubjects() {
        log.debug("REST request to get all HireMeSubjects");
        return hireMeSubjectService.findAll();
    }

    /**
     * {@code GET  /hire-me-subjects/:id} : get the "id" hireMeSubject.
     *
     * @param id the id of the hireMeSubjectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hireMeSubjectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hire-me-subjects/{id}")
    public ResponseEntity<HireMeSubjectDTO> getHireMeSubject(@PathVariable Long id) {
        log.debug("REST request to get HireMeSubject : {}", id);
        Optional<HireMeSubjectDTO> hireMeSubjectDTO = hireMeSubjectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hireMeSubjectDTO);
    }

    /**
     * {@code DELETE  /hire-me-subjects/:id} : delete the "id" hireMeSubject.
     *
     * @param id the id of the hireMeSubjectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hire-me-subjects/{id}")
    public ResponseEntity<Void> deleteHireMeSubject(@PathVariable Long id) {
        log.debug("REST request to delete HireMeSubject : {}", id);
        hireMeSubjectService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
