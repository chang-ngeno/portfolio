package io.changsoft.portfolio.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.changsoft.portfolio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkExperienceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkExperience.class);
        WorkExperience workExperience1 = new WorkExperience();
        workExperience1.setId(1L);
        WorkExperience workExperience2 = new WorkExperience();
        workExperience2.setId(workExperience1.getId());
        assertThat(workExperience1).isEqualTo(workExperience2);
        workExperience2.setId(2L);
        assertThat(workExperience1).isNotEqualTo(workExperience2);
        workExperience1.setId(null);
        assertThat(workExperience1).isNotEqualTo(workExperience2);
    }
}
