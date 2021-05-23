package io.changsoft.portfolio.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.changsoft.portfolio.IntegrationTest;
import io.changsoft.portfolio.domain.PersonalDetails;
import io.changsoft.portfolio.repository.PersonalDetailsRepository;
import io.changsoft.portfolio.service.dto.PersonalDetailsDTO;
import io.changsoft.portfolio.service.mapper.PersonalDetailsMapper;
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
 * Integration tests for the {@link PersonalDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonalDetailsResourceIT {

    private static final String DEFAULT_NAMES = "AAAAAAAAAA";
    private static final String UPDATED_NAMES = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/personal-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    @Autowired
    private PersonalDetailsMapper personalDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonalDetailsMockMvc;

    private PersonalDetails personalDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalDetails createEntity(EntityManager em) {
        PersonalDetails personalDetails = new PersonalDetails()
            .names(DEFAULT_NAMES)
            .slug(DEFAULT_SLUG)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return personalDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalDetails createUpdatedEntity(EntityManager em) {
        PersonalDetails personalDetails = new PersonalDetails()
            .names(UPDATED_NAMES)
            .slug(UPDATED_SLUG)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return personalDetails;
    }

    @BeforeEach
    public void initTest() {
        personalDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonalDetails() throws Exception {
        int databaseSizeBeforeCreate = personalDetailsRepository.findAll().size();
        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);
        restPersonalDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PersonalDetails testPersonalDetails = personalDetailsList.get(personalDetailsList.size() - 1);
        assertThat(testPersonalDetails.getNames()).isEqualTo(DEFAULT_NAMES);
        assertThat(testPersonalDetails.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testPersonalDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonalDetails.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void createPersonalDetailsWithExistingId() throws Exception {
        // Create the PersonalDetails with an existing ID
        personalDetails.setId(1L);
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        int databaseSizeBeforeCreate = personalDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNamesIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalDetailsRepository.findAll().size();
        // set the field null
        personalDetails.setNames(null);

        // Create the PersonalDetails, which fails.
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        restPersonalDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalDetailsRepository.findAll().size();
        // set the field null
        personalDetails.setEmail(null);

        // Create the PersonalDetails, which fails.
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        restPersonalDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonalDetails() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        // Get all the personalDetailsList
        restPersonalDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].names").value(hasItem(DEFAULT_NAMES)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    @Transactional
    void getPersonalDetails() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        // Get the personalDetails
        restPersonalDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, personalDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personalDetails.getId().intValue()))
            .andExpect(jsonPath("$.names").value(DEFAULT_NAMES))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingPersonalDetails() throws Exception {
        // Get the personalDetails
        restPersonalDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonalDetails() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();

        // Update the personalDetails
        PersonalDetails updatedPersonalDetails = personalDetailsRepository.findById(personalDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPersonalDetails are not directly saved in db
        em.detach(updatedPersonalDetails);
        updatedPersonalDetails.names(UPDATED_NAMES).slug(UPDATED_SLUG).email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(updatedPersonalDetails);

        restPersonalDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
        PersonalDetails testPersonalDetails = personalDetailsList.get(personalDetailsList.size() - 1);
        assertThat(testPersonalDetails.getNames()).isEqualTo(UPDATED_NAMES);
        assertThat(testPersonalDetails.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testPersonalDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonalDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonalDetailsWithPatch() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();

        // Update the personalDetails using partial update
        PersonalDetails partialUpdatedPersonalDetails = new PersonalDetails();
        partialUpdatedPersonalDetails.setId(personalDetails.getId());

        partialUpdatedPersonalDetails.slug(UPDATED_SLUG);

        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonalDetails))
            )
            .andExpect(status().isOk());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
        PersonalDetails testPersonalDetails = personalDetailsList.get(personalDetailsList.size() - 1);
        assertThat(testPersonalDetails.getNames()).isEqualTo(DEFAULT_NAMES);
        assertThat(testPersonalDetails.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testPersonalDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPersonalDetails.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdatePersonalDetailsWithPatch() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();

        // Update the personalDetails using partial update
        PersonalDetails partialUpdatedPersonalDetails = new PersonalDetails();
        partialUpdatedPersonalDetails.setId(personalDetails.getId());

        partialUpdatedPersonalDetails.names(UPDATED_NAMES).slug(UPDATED_SLUG).email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);

        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonalDetails))
            )
            .andExpect(status().isOk());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
        PersonalDetails testPersonalDetails = personalDetailsList.get(personalDetailsList.size() - 1);
        assertThat(testPersonalDetails.getNames()).isEqualTo(UPDATED_NAMES);
        assertThat(testPersonalDetails.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testPersonalDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPersonalDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personalDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonalDetails() throws Exception {
        int databaseSizeBeforeUpdate = personalDetailsRepository.findAll().size();
        personalDetails.setId(count.incrementAndGet());

        // Create the PersonalDetails
        PersonalDetailsDTO personalDetailsDTO = personalDetailsMapper.toDto(personalDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonalDetails in the database
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonalDetails() throws Exception {
        // Initialize the database
        personalDetailsRepository.saveAndFlush(personalDetails);

        int databaseSizeBeforeDelete = personalDetailsRepository.findAll().size();

        // Delete the personalDetails
        restPersonalDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, personalDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonalDetails> personalDetailsList = personalDetailsRepository.findAll();
        assertThat(personalDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
