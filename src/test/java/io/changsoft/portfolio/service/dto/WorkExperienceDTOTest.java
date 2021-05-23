package io.changsoft.portfolio.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.changsoft.portfolio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkExperienceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkExperienceDTO.class);
        WorkExperienceDTO workExperienceDTO1 = new WorkExperienceDTO();
        workExperienceDTO1.setId(1L);
        WorkExperienceDTO workExperienceDTO2 = new WorkExperienceDTO();
        assertThat(workExperienceDTO1).isNotEqualTo(workExperienceDTO2);
        workExperienceDTO2.setId(workExperienceDTO1.getId());
        assertThat(workExperienceDTO1).isEqualTo(workExperienceDTO2);
        workExperienceDTO2.setId(2L);
        assertThat(workExperienceDTO1).isNotEqualTo(workExperienceDTO2);
        workExperienceDTO1.setId(null);
        assertThat(workExperienceDTO1).isNotEqualTo(workExperienceDTO2);
    }
}
