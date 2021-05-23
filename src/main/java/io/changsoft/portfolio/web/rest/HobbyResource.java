package io.changsoft.portfolio.web.rest;

import io.changsoft.portfolio.repository.HobbyRepository;
import io.changsoft.portfolio.service.HobbyService;
import io.changsoft.portfolio.service.dto.HobbyDTO;
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
 * REST controller for managing {@link io.changsoft.portfolio.domain.Hobby}.
 */
@RestController
@RequestMapping("/api")
public class HobbyResource {

    private final Logger log = LoggerFactory.getLogger(HobbyResource.class);

    private static final String ENTITY_NAME = "hobby";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HobbyService hobbyService;

    private final HobbyRepository hobbyRepository;

    public HobbyResource(HobbyService hobbyService, HobbyRepository hobbyRepository) {
        this.hobbyService = hobbyService;
        this.hobbyRepository = hobbyRepository;
    }

    /**
     * {@code POST  /hobbies} : Create a new hobby.
     *
     * @param hobbyDTO the hobbyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hobbyDTO, or with status {@code 400 (Bad Request)} if the hobby has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hobbies")
    public ResponseEntity<HobbyDTO> createHobby(@Valid @RequestBody HobbyDTO hobbyDTO) throws URISyntaxException {
        log.debug("REST request to save Hobby : {}", hobbyDTO);
        if (hobbyDTO.getId() != null) {
            throw new BadRequestAlertException("A new hobby cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HobbyDTO result = hobbyService.save(hobbyDTO);
        return ResponseEntity
            .created(new URI("/api/hobbies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hobbies/:id} : Updates an existing hobby.
     *
     * @param id the id of the hobbyDTO to save.
     * @param hobbyDTO the hobbyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hobbyDTO,
     * or with status {@code 400 (Bad Request)} if the hobbyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hobbyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hobbies/{id}")
    public ResponseEntity<HobbyDTO> updateHobby(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HobbyDTO hobbyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Hobby : {}, {}", id, hobbyDTO);
        if (hobbyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hobbyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hobbyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HobbyDTO result = hobbyService.save(hobbyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hobbyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hobbies/:id} : Partial updates given fields of an existing hobby, field will ignore if it is null
     *
     * @param id the id of the hobbyDTO to save.
     * @param hobbyDTO the hobbyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hobbyDTO,
     * or with status {@code 400 (Bad Request)} if the hobbyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hobbyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hobbyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hobbies/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HobbyDTO> partialUpdateHobby(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HobbyDTO hobbyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hobby partially : {}, {}", id, hobbyDTO);
        if (hobbyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hobbyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hobbyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HobbyDTO> result = hobbyService.partialUpdate(hobbyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hobbyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hobbies} : get all the hobbies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hobbies in body.
     */
    @GetMapping("/hobbies")
    public ResponseEntity<List<HobbyDTO>> getAllHobbies(Pageable pageable) {
        log.debug("REST request to get a page of Hobbies");
        Page<HobbyDTO> page = hobbyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hobbies/:id} : get the "id" hobby.
     *
     * @param id the id of the hobbyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hobbyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hobbies/{id}")
    public ResponseEntity<HobbyDTO> getHobby(@PathVariable Long id) {
        log.debug("REST request to get Hobby : {}", id);
        Optional<HobbyDTO> hobbyDTO = hobbyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hobbyDTO);
    }

    /**
     * {@code DELETE  /hobbies/:id} : delete the "id" hobby.
     *
     * @param id the id of the hobbyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hobbies/{id}")
    public ResponseEntity<Void> deleteHobby(@PathVariable Long id) {
        log.debug("REST request to delete Hobby : {}", id);
        hobbyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
