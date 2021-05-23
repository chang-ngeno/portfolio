package io.changsoft.portfolio.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.changsoft.portfolio.IntegrationTest;
import io.changsoft.portfolio.domain.WorkExperience;
import io.changsoft.portfolio.repository.WorkExperienceRepository;
import io.changsoft.portfolio.service.dto.WorkExperienceDTO;
import io.changsoft.portfolio.service.mapper.WorkExperienceMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link WorkExperienceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkExperienceResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYER = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYER = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ROLES = "AAAAAAAAAA";
    private static final String UPDATED_ROLES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/work-experiences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private WorkExperienceMapper workExperienceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkExperienceMockMvc;

    private WorkExperience workExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkExperience createEntity(EntityManager em) {
        WorkExperience workExperience = new WorkExperience()
            .title(DEFAULT_TITLE)
            .employer(DEFAULT_EMPLOYER)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .roles(DEFAULT_ROLES);
        return workExperience;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkExperience createUpdatedEntity(EntityManager em) {
        WorkExperience workExperience = new WorkExperience()
            .title(UPDATED_TITLE)
            .employer(UPDATED_EMPLOYER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .roles(UPDATED_ROLES);
        return workExperience;
    }

    @BeforeEach
    public void initTest() {
        workExperience = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkExperience() throws Exception {
        int databaseSizeBeforeCreate = workExperienceRepository.findAll().size();
        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);
        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testWorkExperience.getEmployer()).isEqualTo(DEFAULT_EMPLOYER);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWorkExperience.getRoles()).isEqualTo(DEFAULT_ROLES);
    }

    @Test
    @Transactional
    void createWorkExperienceWithExistingId() throws Exception {
        // Create the WorkExperience with an existing ID
        workExperience.setId(1L);
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        int databaseSizeBeforeCreate = workExperienceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = workExperienceRepository.findAll().size();
        // set the field null
        workExperience.setTitle(null);

        // Create the WorkExperience, which fails.
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmployerIsRequired() throws Exception {
        int databaseSizeBeforeTest = workExperienceRepository.findAll().size();
        // set the field null
        workExperience.setEmployer(null);

        // Create the WorkExperience, which fails.
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workExperienceRepository.findAll().size();
        // set the field null
        workExperience.setStartDate(null);

        // Create the WorkExperience, which fails.
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkExperiences() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].employer").value(hasItem(DEFAULT_EMPLOYER)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].roles").value(hasItem(DEFAULT_ROLES.toString())));
    }

    @Test
    @Transactional
    void getWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get the workExperience
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL_ID, workExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workExperience.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.employer").value(DEFAULT_EMPLOYER))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.roles").value(DEFAULT_ROLES.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWorkExperience() throws Exception {
        // Get the workExperience
        restWorkExperienceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience
        WorkExperience updatedWorkExperience = workExperienceRepository.findById(workExperience.getId()).get();
        // Disconnect from session so that the updates on updatedWorkExperience are not directly saved in db
        em.detach(updatedWorkExperience);
        updatedWorkExperience
            .title(UPDATED_TITLE)
            .employer(UPDATED_EMPLOYER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .roles(UPDATED_ROLES);
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(updatedWorkExperience);

        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWorkExperience.getEmployer()).isEqualTo(UPDATED_EMPLOYER);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getRoles()).isEqualTo(UPDATED_ROLES);
    }

    @Test
    @Transactional
    void putNonExistingWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkExperienceWithPatch() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience using partial update
        WorkExperience partialUpdatedWorkExperience = new WorkExperience();
        partialUpdatedWorkExperience.setId(workExperience.getId());

        partialUpdatedWorkExperience.title(UPDATED_TITLE).startDate(UPDATED_START_DATE).roles(UPDATED_ROLES);

        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkExperience))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWorkExperience.getEmployer()).isEqualTo(DEFAULT_EMPLOYER);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWorkExperience.getRoles()).isEqualTo(UPDATED_ROLES);
    }

    @Test
    @Transactional
    void fullUpdateWorkExperienceWithPatch() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience using partial update
        WorkExperience partialUpdatedWorkExperience = new WorkExperience();
        partialUpdatedWorkExperience.setId(workExperience.getId());

        partialUpdatedWorkExperience
            .title(UPDATED_TITLE)
            .employer(UPDATED_EMPLOYER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .roles(UPDATED_ROLES);

        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkExperience))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWorkExperience.getEmployer()).isEqualTo(UPDATED_EMPLOYER);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getRoles()).isEqualTo(UPDATED_ROLES);
    }

    @Test
    @Transactional
    void patchNonExistingWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeDelete = workExperienceRepository.findAll().size();

        // Delete the workExperience
        restWorkExperienceMockMvc
            .perform(delete(ENTITY_API_URL_ID, workExperience.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
