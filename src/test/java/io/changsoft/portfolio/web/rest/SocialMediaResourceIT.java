package io.changsoft.portfolio.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.changsoft.portfolio.IntegrationTest;
import io.changsoft.portfolio.domain.SocialMedia;
import io.changsoft.portfolio.repository.SocialMediaRepository;
import io.changsoft.portfolio.service.dto.SocialMediaDTO;
import io.changsoft.portfolio.service.mapper.SocialMediaMapper;
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
 * Integration tests for the {@link SocialMediaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocialMediaResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL_LINK = "AAAAAAAAAA";
    private static final String UPDATED_URL_LINK = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLISHED = false;
    private static final Boolean UPDATED_PUBLISHED = true;

    private static final String ENTITY_API_URL = "/api/social-medias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private SocialMediaMapper socialMediaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocialMediaMockMvc;

    private SocialMedia socialMedia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialMedia createEntity(EntityManager em) {
        SocialMedia socialMedia = new SocialMedia().username(DEFAULT_USERNAME).urlLink(DEFAULT_URL_LINK).published(DEFAULT_PUBLISHED);
        return socialMedia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialMedia createUpdatedEntity(EntityManager em) {
        SocialMedia socialMedia = new SocialMedia().username(UPDATED_USERNAME).urlLink(UPDATED_URL_LINK).published(UPDATED_PUBLISHED);
        return socialMedia;
    }

    @BeforeEach
    public void initTest() {
        socialMedia = createEntity(em);
    }

    @Test
    @Transactional
    void createSocialMedia() throws Exception {
        int databaseSizeBeforeCreate = socialMediaRepository.findAll().size();
        // Create the SocialMedia
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);
        restSocialMediaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeCreate + 1);
        SocialMedia testSocialMedia = socialMediaList.get(socialMediaList.size() - 1);
        assertThat(testSocialMedia.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSocialMedia.getUrlLink()).isEqualTo(DEFAULT_URL_LINK);
        assertThat(testSocialMedia.getPublished()).isEqualTo(DEFAULT_PUBLISHED);
    }

    @Test
    @Transactional
    void createSocialMediaWithExistingId() throws Exception {
        // Create the SocialMedia with an existing ID
        socialMedia.setId(1L);
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        int databaseSizeBeforeCreate = socialMediaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialMediaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialMediaRepository.findAll().size();
        // set the field null
        socialMedia.setUsername(null);

        // Create the SocialMedia, which fails.
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        restSocialMediaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isBadRequest());

        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUrlLinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialMediaRepository.findAll().size();
        // set the field null
        socialMedia.setUrlLink(null);

        // Create the SocialMedia, which fails.
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        restSocialMediaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isBadRequest());

        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPublishedIsRequired() throws Exception {
        int databaseSizeBeforeTest = socialMediaRepository.findAll().size();
        // set the field null
        socialMedia.setPublished(null);

        // Create the SocialMedia, which fails.
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        restSocialMediaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isBadRequest());

        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocialMedias() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        // Get all the socialMediaList
        restSocialMediaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].urlLink").value(hasItem(DEFAULT_URL_LINK)))
            .andExpect(jsonPath("$.[*].published").value(hasItem(DEFAULT_PUBLISHED.booleanValue())));
    }

    @Test
    @Transactional
    void getSocialMedia() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        // Get the socialMedia
        restSocialMediaMockMvc
            .perform(get(ENTITY_API_URL_ID, socialMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socialMedia.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.urlLink").value(DEFAULT_URL_LINK))
            .andExpect(jsonPath("$.published").value(DEFAULT_PUBLISHED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSocialMedia() throws Exception {
        // Get the socialMedia
        restSocialMediaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocialMedia() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();

        // Update the socialMedia
        SocialMedia updatedSocialMedia = socialMediaRepository.findById(socialMedia.getId()).get();
        // Disconnect from session so that the updates on updatedSocialMedia are not directly saved in db
        em.detach(updatedSocialMedia);
        updatedSocialMedia.username(UPDATED_USERNAME).urlLink(UPDATED_URL_LINK).published(UPDATED_PUBLISHED);
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(updatedSocialMedia);

        restSocialMediaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socialMediaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
        SocialMedia testSocialMedia = socialMediaList.get(socialMediaList.size() - 1);
        assertThat(testSocialMedia.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSocialMedia.getUrlLink()).isEqualTo(UPDATED_URL_LINK);
        assertThat(testSocialMedia.getPublished()).isEqualTo(UPDATED_PUBLISHED);
    }

    @Test
    @Transactional
    void putNonExistingSocialMedia() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();
        socialMedia.setId(count.incrementAndGet());

        // Create the SocialMedia
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialMediaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socialMediaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocialMedia() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();
        socialMedia.setId(count.incrementAndGet());

        // Create the SocialMedia
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialMediaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocialMedia() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();
        socialMedia.setId(count.incrementAndGet());

        // Create the SocialMedia
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialMediaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(socialMediaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocialMediaWithPatch() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();

        // Update the socialMedia using partial update
        SocialMedia partialUpdatedSocialMedia = new SocialMedia();
        partialUpdatedSocialMedia.setId(socialMedia.getId());

        partialUpdatedSocialMedia.urlLink(UPDATED_URL_LINK);

        restSocialMediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialMedia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialMedia))
            )
            .andExpect(status().isOk());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
        SocialMedia testSocialMedia = socialMediaList.get(socialMediaList.size() - 1);
        assertThat(testSocialMedia.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSocialMedia.getUrlLink()).isEqualTo(UPDATED_URL_LINK);
        assertThat(testSocialMedia.getPublished()).isEqualTo(DEFAULT_PUBLISHED);
    }

    @Test
    @Transactional
    void fullUpdateSocialMediaWithPatch() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();

        // Update the socialMedia using partial update
        SocialMedia partialUpdatedSocialMedia = new SocialMedia();
        partialUpdatedSocialMedia.setId(socialMedia.getId());

        partialUpdatedSocialMedia.username(UPDATED_USERNAME).urlLink(UPDATED_URL_LINK).published(UPDATED_PUBLISHED);

        restSocialMediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialMedia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialMedia))
            )
            .andExpect(status().isOk());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
        SocialMedia testSocialMedia = socialMediaList.get(socialMediaList.size() - 1);
        assertThat(testSocialMedia.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSocialMedia.getUrlLink()).isEqualTo(UPDATED_URL_LINK);
        assertThat(testSocialMedia.getPublished()).isEqualTo(UPDATED_PUBLISHED);
    }

    @Test
    @Transactional
    void patchNonExistingSocialMedia() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();
        socialMedia.setId(count.incrementAndGet());

        // Create the SocialMedia
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialMediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socialMediaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocialMedia() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();
        socialMedia.setId(count.incrementAndGet());

        // Create the SocialMedia
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialMediaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocialMedia() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();
        socialMedia.setId(count.incrementAndGet());

        // Create the SocialMedia
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialMediaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(socialMediaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocialMedia() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        int databaseSizeBeforeDelete = socialMediaRepository.findAll().size();

        // Delete the socialMedia
        restSocialMediaMockMvc
            .perform(delete(ENTITY_API_URL_ID, socialMedia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
