package io.changsoft.portfolio.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.changsoft.portfolio.IntegrationTest;
import io.changsoft.portfolio.domain.HireMeSubject;
import io.changsoft.portfolio.repository.HireMeSubjectRepository;
import io.changsoft.portfolio.service.dto.HireMeSubjectDTO;
import io.changsoft.portfolio.service.mapper.HireMeSubjectMapper;
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

/**
 * Integration tests for the {@link HireMeSubjectResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HireMeSubjectResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hire-me-subjects";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HireMeSubjectRepository hireMeSubjectRepository;

    @Autowired
    private HireMeSubjectMapper hireMeSubjectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHireMeSubjectMockMvc;

    private HireMeSubject hireMeSubject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HireMeSubject createEntity(EntityManager em) {
        HireMeSubject hireMeSubject = new HireMeSubject().subject(DEFAULT_SUBJECT);
        return hireMeSubject;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HireMeSubject createUpdatedEntity(EntityManager em) {
        HireMeSubject hireMeSubject = new HireMeSubject().subject(UPDATED_SUBJECT);
        return hireMeSubject;
    }

    @BeforeEach
    public void initTest() {
        hireMeSubject = createEntity(em);
    }

    @Test
    @Transactional
    void createHireMeSubject() throws Exception {
        int databaseSizeBeforeCreate = hireMeSubjectRepository.findAll().size();
        // Create the HireMeSubject
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(hireMeSubject);
        restHireMeSubjectMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isCreated());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeCreate + 1);
        HireMeSubject testHireMeSubject = hireMeSubjectList.get(hireMeSubjectList.size() - 1);
        assertThat(testHireMeSubject.getSubject()).isEqualTo(DEFAULT_SUBJECT);
    }

    @Test
    @Transactional
    void createHireMeSubjectWithExistingId() throws Exception {
        // Create the HireMeSubject with an existing ID
        hireMeSubject.setId(1L);
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(hireMeSubject);

        int databaseSizeBeforeCreate = hireMeSubjectRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHireMeSubjectMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = hireMeSubjectRepository.findAll().size();
        // set the field null
        hireMeSubject.setSubject(null);

        // Create the HireMeSubject, which fails.
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(hireMeSubject);

        restHireMeSubjectMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isBadRequest());

        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHireMeSubjects() throws Exception {
        // Initialize the database
        hireMeSubjectRepository.saveAndFlush(hireMeSubject);

        // Get all the hireMeSubjectList
        restHireMeSubjectMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hireMeSubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)));
    }

    @Test
    @Transactional
    void getHireMeSubject() throws Exception {
        // Initialize the database
        hireMeSubjectRepository.saveAndFlush(hireMeSubject);

        // Get the hireMeSubject
        restHireMeSubjectMockMvc
            .perform(get(ENTITY_API_URL_ID, hireMeSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hireMeSubject.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT));
    }

    @Test
    @Transactional
    void getNonExistingHireMeSubject() throws Exception {
        // Get the hireMeSubject
        restHireMeSubjectMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHireMeSubject() throws Exception {
        // Initialize the database
        hireMeSubjectRepository.saveAndFlush(hireMeSubject);

        int databaseSizeBeforeUpdate = hireMeSubjectRepository.findAll().size();

        // Update the hireMeSubject
        HireMeSubject updatedHireMeSubject = hireMeSubjectRepository.findById(hireMeSubject.getId()).get();
        // Disconnect from session so that the updates on updatedHireMeSubject are not directly saved in db
        em.detach(updatedHireMeSubject);
        updatedHireMeSubject.subject(UPDATED_SUBJECT);
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(updatedHireMeSubject);

        restHireMeSubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hireMeSubjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isOk());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeUpdate);
        HireMeSubject testHireMeSubject = hireMeSubjectList.get(hireMeSubjectList.size() - 1);
        assertThat(testHireMeSubject.getSubject()).isEqualTo(UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void putNonExistingHireMeSubject() throws Exception {
        int databaseSizeBeforeUpdate = hireMeSubjectRepository.findAll().size();
        hireMeSubject.setId(count.incrementAndGet());

        // Create the HireMeSubject
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(hireMeSubject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHireMeSubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hireMeSubjectDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHireMeSubject() throws Exception {
        int databaseSizeBeforeUpdate = hireMeSubjectRepository.findAll().size();
        hireMeSubject.setId(count.incrementAndGet());

        // Create the HireMeSubject
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(hireMeSubject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHireMeSubjectMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHireMeSubject() throws Exception {
        int databaseSizeBeforeUpdate = hireMeSubjectRepository.findAll().size();
        hireMeSubject.setId(count.incrementAndGet());

        // Create the HireMeSubject
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(hireMeSubject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHireMeSubjectMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHireMeSubjectWithPatch() throws Exception {
        // Initialize the database
        hireMeSubjectRepository.saveAndFlush(hireMeSubject);

        int databaseSizeBeforeUpdate = hireMeSubjectRepository.findAll().size();

        // Update the hireMeSubject using partial update
        HireMeSubject partialUpdatedHireMeSubject = new HireMeSubject();
        partialUpdatedHireMeSubject.setId(hireMeSubject.getId());

        partialUpdatedHireMeSubject.subject(UPDATED_SUBJECT);

        restHireMeSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHireMeSubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHireMeSubject))
            )
            .andExpect(status().isOk());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeUpdate);
        HireMeSubject testHireMeSubject = hireMeSubjectList.get(hireMeSubjectList.size() - 1);
        assertThat(testHireMeSubject.getSubject()).isEqualTo(UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void fullUpdateHireMeSubjectWithPatch() throws Exception {
        // Initialize the database
        hireMeSubjectRepository.saveAndFlush(hireMeSubject);

        int databaseSizeBeforeUpdate = hireMeSubjectRepository.findAll().size();

        // Update the hireMeSubject using partial update
        HireMeSubject partialUpdatedHireMeSubject = new HireMeSubject();
        partialUpdatedHireMeSubject.setId(hireMeSubject.getId());

        partialUpdatedHireMeSubject.subject(UPDATED_SUBJECT);

        restHireMeSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHireMeSubject.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHireMeSubject))
            )
            .andExpect(status().isOk());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeUpdate);
        HireMeSubject testHireMeSubject = hireMeSubjectList.get(hireMeSubjectList.size() - 1);
        assertThat(testHireMeSubject.getSubject()).isEqualTo(UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    void patchNonExistingHireMeSubject() throws Exception {
        int databaseSizeBeforeUpdate = hireMeSubjectRepository.findAll().size();
        hireMeSubject.setId(count.incrementAndGet());

        // Create the HireMeSubject
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(hireMeSubject);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHireMeSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hireMeSubjectDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHireMeSubject() throws Exception {
        int databaseSizeBeforeUpdate = hireMeSubjectRepository.findAll().size();
        hireMeSubject.setId(count.incrementAndGet());

        // Create the HireMeSubject
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(hireMeSubject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHireMeSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHireMeSubject() throws Exception {
        int databaseSizeBeforeUpdate = hireMeSubjectRepository.findAll().size();
        hireMeSubject.setId(count.incrementAndGet());

        // Create the HireMeSubject
        HireMeSubjectDTO hireMeSubjectDTO = hireMeSubjectMapper.toDto(hireMeSubject);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHireMeSubjectMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hireMeSubjectDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HireMeSubject in the database
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHireMeSubject() throws Exception {
        // Initialize the database
        hireMeSubjectRepository.saveAndFlush(hireMeSubject);

        int databaseSizeBeforeDelete = hireMeSubjectRepository.findAll().size();

        // Delete the hireMeSubject
        restHireMeSubjectMockMvc
            .perform(delete(ENTITY_API_URL_ID, hireMeSubject.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HireMeSubject> hireMeSubjectList = hireMeSubjectRepository.findAll();
        assertThat(hireMeSubjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
