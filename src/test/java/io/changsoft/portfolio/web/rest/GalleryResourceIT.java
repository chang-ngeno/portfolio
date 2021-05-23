package io.changsoft.portfolio.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.changsoft.portfolio.IntegrationTest;
import io.changsoft.portfolio.domain.Gallery;
import io.changsoft.portfolio.repository.GalleryRepository;
import io.changsoft.portfolio.service.dto.GalleryDTO;
import io.changsoft.portfolio.service.mapper.GalleryMapper;
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
 * Integration tests for the {@link GalleryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GalleryResourceIT {

    private static final String DEFAULT_GALLERY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GALLERY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/galleries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private GalleryMapper galleryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGalleryMockMvc;

    private Gallery gallery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gallery createEntity(EntityManager em) {
        Gallery gallery = new Gallery()
            .galleryName(DEFAULT_GALLERY_NAME)
            .slug(DEFAULT_SLUG)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return gallery;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gallery createUpdatedEntity(EntityManager em) {
        Gallery gallery = new Gallery()
            .galleryName(UPDATED_GALLERY_NAME)
            .slug(UPDATED_SLUG)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        return gallery;
    }

    @BeforeEach
    public void initTest() {
        gallery = createEntity(em);
    }

    @Test
    @Transactional
    void createGallery() throws Exception {
        int databaseSizeBeforeCreate = galleryRepository.findAll().size();
        // Create the Gallery
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);
        restGalleryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galleryDTO)))
            .andExpect(status().isCreated());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate + 1);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getGalleryName()).isEqualTo(DEFAULT_GALLERY_NAME);
        assertThat(testGallery.getSlug()).isEqualTo(DEFAULT_SLUG);
        assertThat(testGallery.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testGallery.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createGalleryWithExistingId() throws Exception {
        // Create the Gallery with an existing ID
        gallery.setId(1L);
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        int databaseSizeBeforeCreate = galleryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalleryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galleryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGalleryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = galleryRepository.findAll().size();
        // set the field null
        gallery.setGalleryName(null);

        // Create the Gallery, which fails.
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        restGalleryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galleryDTO)))
            .andExpect(status().isBadRequest());

        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGalleries() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get all the galleryList
        restGalleryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gallery.getId().intValue())))
            .andExpect(jsonPath("$.[*].galleryName").value(hasItem(DEFAULT_GALLERY_NAME)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    void getGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        // Get the gallery
        restGalleryMockMvc
            .perform(get(ENTITY_API_URL_ID, gallery.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gallery.getId().intValue()))
            .andExpect(jsonPath("$.galleryName").value(DEFAULT_GALLERY_NAME))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    void getNonExistingGallery() throws Exception {
        // Get the gallery
        restGalleryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Update the gallery
        Gallery updatedGallery = galleryRepository.findById(gallery.getId()).get();
        // Disconnect from session so that the updates on updatedGallery are not directly saved in db
        em.detach(updatedGallery);
        updatedGallery
            .galleryName(UPDATED_GALLERY_NAME)
            .slug(UPDATED_SLUG)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        GalleryDTO galleryDTO = galleryMapper.toDto(updatedGallery);

        restGalleryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, galleryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(galleryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getGalleryName()).isEqualTo(UPDATED_GALLERY_NAME);
        assertThat(testGallery.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testGallery.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testGallery.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();
        gallery.setId(count.incrementAndGet());

        // Create the Gallery
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGalleryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, galleryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(galleryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();
        gallery.setId(count.incrementAndGet());

        // Create the Gallery
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGalleryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(galleryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();
        gallery.setId(count.incrementAndGet());

        // Create the Gallery
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGalleryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galleryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGalleryWithPatch() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Update the gallery using partial update
        Gallery partialUpdatedGallery = new Gallery();
        partialUpdatedGallery.setId(gallery.getId());

        partialUpdatedGallery
            .galleryName(UPDATED_GALLERY_NAME)
            .slug(UPDATED_SLUG)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restGalleryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGallery.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGallery))
            )
            .andExpect(status().isOk());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getGalleryName()).isEqualTo(UPDATED_GALLERY_NAME);
        assertThat(testGallery.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testGallery.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testGallery.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateGalleryWithPatch() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();

        // Update the gallery using partial update
        Gallery partialUpdatedGallery = new Gallery();
        partialUpdatedGallery.setId(gallery.getId());

        partialUpdatedGallery
            .galleryName(UPDATED_GALLERY_NAME)
            .slug(UPDATED_SLUG)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restGalleryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGallery.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGallery))
            )
            .andExpect(status().isOk());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
        Gallery testGallery = galleryList.get(galleryList.size() - 1);
        assertThat(testGallery.getGalleryName()).isEqualTo(UPDATED_GALLERY_NAME);
        assertThat(testGallery.getSlug()).isEqualTo(UPDATED_SLUG);
        assertThat(testGallery.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testGallery.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();
        gallery.setId(count.incrementAndGet());

        // Create the Gallery
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGalleryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, galleryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(galleryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();
        gallery.setId(count.incrementAndGet());

        // Create the Gallery
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGalleryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(galleryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGallery() throws Exception {
        int databaseSizeBeforeUpdate = galleryRepository.findAll().size();
        gallery.setId(count.incrementAndGet());

        // Create the Gallery
        GalleryDTO galleryDTO = galleryMapper.toDto(gallery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGalleryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(galleryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Gallery in the database
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGallery() throws Exception {
        // Initialize the database
        galleryRepository.saveAndFlush(gallery);

        int databaseSizeBeforeDelete = galleryRepository.findAll().size();

        // Delete the gallery
        restGalleryMockMvc
            .perform(delete(ENTITY_API_URL_ID, gallery.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gallery> galleryList = galleryRepository.findAll();
        assertThat(galleryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
