package io.changsoft.portfolio.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.changsoft.portfolio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HobbyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HobbyDTO.class);
        HobbyDTO hobbyDTO1 = new HobbyDTO();
        hobbyDTO1.setId(1L);
        HobbyDTO hobbyDTO2 = new HobbyDTO();
        assertThat(hobbyDTO1).isNotEqualTo(hobbyDTO2);
        hobbyDTO2.setId(hobbyDTO1.getId());
        assertThat(hobbyDTO1).isEqualTo(hobbyDTO2);
        hobbyDTO2.setId(2L);
        assertThat(hobbyDTO1).isNotEqualTo(hobbyDTO2);
        hobbyDTO1.setId(null);
        assertThat(hobbyDTO1).isNotEqualTo(hobbyDTO2);
    }
}
