package io.changsoft.portfolio.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.changsoft.portfolio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HireMeSubjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HireMeSubject.class);
        HireMeSubject hireMeSubject1 = new HireMeSubject();
        hireMeSubject1.setId(1L);
        HireMeSubject hireMeSubject2 = new HireMeSubject();
        hireMeSubject2.setId(hireMeSubject1.getId());
        assertThat(hireMeSubject1).isEqualTo(hireMeSubject2);
        hireMeSubject2.setId(2L);
        assertThat(hireMeSubject1).isNotEqualTo(hireMeSubject2);
        hireMeSubject1.setId(null);
        assertThat(hireMeSubject1).isNotEqualTo(hireMeSubject2);
    }
}
