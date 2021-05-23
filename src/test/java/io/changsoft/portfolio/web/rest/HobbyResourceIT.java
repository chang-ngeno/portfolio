package io.changsoft.portfolio.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.changsoft.portfolio.IntegrationTest;
import io.changsoft.portfolio.domain.Hobby;
import io.changsoft.portfolio.repository.HobbyRepository;
import io.changsoft.portfolio.service.dto.HobbyDTO;
import io.changsoft.portfolio.service.mapper.HobbyMapper;
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
 * Integration tests for the {@link HobbyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HobbyResourceIT {

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hobbies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private HobbyMapper hobbyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHobbyMockMvc;

    private Hobby hobby;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hobby createEntity(EntityManager em) {
        Hobby hobby = new Hobby().slug(DEFAULT_SLUG);
        return hobby;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hobby createUpdatedEntity(EntityManager em) {
        Hobby hobby = new Hobby().slug(UPDATED_SLUG);
        return hobby;
    }

    @BeforeEach
    public void initTest() {
        hobby = createEntity(em);
    }

    @Test
    @Transactional
    void createHobby() throws Exception {
        int databaseSizeBeforeCreate = hobbyRepository.findAll().size();
        // Create the Hobby
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);
        restHobbyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hobbyDTO)))
            .andExpect(status().isCreated());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeCreate + 1);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getSlug()).isEqualTo(DEFAULT_SLUG);
    }

    @Test
    @Transactional
    void createHobbyWithExistingId() throws Exception {
        // Create the Hobby with an existing ID
        hobby.setId(1L);
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        int databaseSizeBeforeCreate = hobbyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHobbyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hobbyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = hobbyRepository.findAll().size();
        // set the field null
        hobby.setSlug(null);

        // Create the Hobby, which fails.
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        restHobbyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hobbyDTO)))
            .andExpect(status().isBadRequest());

        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHobbies() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        // Get all the hobbyList
        restHobbyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hobby.getId().intValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)));
    }

    @Test
    @Transactional
    void getHobby() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        // Get the hobby
        restHobbyMockMvc
            .perform(get(ENTITY_API_URL_ID, hobby.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hobby.getId().intValue()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG));
    }

    @Test
    @Transactional
    void getNonExistingHobby() throws Exception {
        // Get the hobby
        restHobbyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHobby() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();

        // Update the hobby
        Hobby updatedHobby = hobbyRepository.findById(hobby.getId()).get();
        // Disconnect from session so that the updates on updatedHobby are not directly saved in db
        em.detach(updatedHobby);
        updatedHobby.slug(UPDATED_SLUG);
        HobbyDTO hobbyDTO = hobbyMapper.toDto(updatedHobby);

        restHobbyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hobbyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hobbyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getSlug()).isEqualTo(UPDATED_SLUG);
    }

    @Test
    @Transactional
    void putNonExistingHobby() throws Exception {
        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();
        hobby.setId(count.incrementAndGet());

        // Create the Hobby
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHobbyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hobbyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hobbyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHobby() throws Exception {
        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();
        hobby.setId(count.incrementAndGet());

        // Create the Hobby
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHobbyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hobbyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHobby() throws Exception {
        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();
        hobby.setId(count.incrementAndGet());

        // Create the Hobby
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHobbyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hobbyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHobbyWithPatch() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();

        // Update the hobby using partial update
        Hobby partialUpdatedHobby = new Hobby();
        partialUpdatedHobby.setId(hobby.getId());

        restHobbyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHobby.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHobby))
            )
            .andExpect(status().isOk());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getSlug()).isEqualTo(DEFAULT_SLUG);
    }

    @Test
    @Transactional
    void fullUpdateHobbyWithPatch() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();

        // Update the hobby using partial update
        Hobby partialUpdatedHobby = new Hobby();
        partialUpdatedHobby.setId(hobby.getId());

        partialUpdatedHobby.slug(UPDATED_SLUG);

        restHobbyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHobby.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHobby))
            )
            .andExpect(status().isOk());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getSlug()).isEqualTo(UPDATED_SLUG);
    }

    @Test
    @Transactional
    void patchNonExistingHobby() throws Exception {
        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();
        hobby.setId(count.incrementAndGet());

        // Create the Hobby
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHobbyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hobbyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hobbyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHobby() throws Exception {
        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();
        hobby.setId(count.incrementAndGet());

        // Create the Hobby
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHobbyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hobbyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHobby() throws Exception {
        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();
        hobby.setId(count.incrementAndGet());

        // Create the Hobby
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHobbyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hobbyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHobby() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        int databaseSizeBeforeDelete = hobbyRepository.findAll().size();

        // Delete the hobby
        restHobbyMockMvc
            .perform(delete(ENTITY_API_URL_ID, hobby.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
