package io.changsoft.portfolio.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.changsoft.portfolio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GalleryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gallery.class);
        Gallery gallery1 = new Gallery();
        gallery1.setId(1L);
        Gallery gallery2 = new Gallery();
        gallery2.setId(gallery1.getId());
        assertThat(gallery1).isEqualTo(gallery2);
        gallery2.setId(2L);
        assertThat(gallery1).isNotEqualTo(gallery2);
        gallery1.setId(null);
        assertThat(gallery1).isNotEqualTo(gallery2);
    }
}
