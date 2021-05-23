package io.changsoft.portfolio.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.changsoft.portfolio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HireMeSubjectDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HireMeSubjectDTO.class);
        HireMeSubjectDTO hireMeSubjectDTO1 = new HireMeSubjectDTO();
        hireMeSubjectDTO1.setId(1L);
        HireMeSubjectDTO hireMeSubjectDTO2 = new HireMeSubjectDTO();
        assertThat(hireMeSubjectDTO1).isNotEqualTo(hireMeSubjectDTO2);
        hireMeSubjectDTO2.setId(hireMeSubjectDTO1.getId());
        assertThat(hireMeSubjectDTO1).isEqualTo(hireMeSubjectDTO2);
        hireMeSubjectDTO2.setId(2L);
        assertThat(hireMeSubjectDTO1).isNotEqualTo(hireMeSubjectDTO2);
        hireMeSubjectDTO1.setId(null);
        assertThat(hireMeSubjectDTO1).isNotEqualTo(hireMeSubjectDTO2);
    }
}
