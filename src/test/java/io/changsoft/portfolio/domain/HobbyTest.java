package io.changsoft.portfolio.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.changsoft.portfolio.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HobbyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hobby.class);
        Hobby hobby1 = new Hobby();
        hobby1.setId(1L);
        Hobby hobby2 = new Hobby();
        hobby2.setId(hobby1.getId());
        assertThat(hobby1).isEqualTo(hobby2);
        hobby2.setId(2L);
        assertThat(hobby1).isNotEqualTo(hobby2);
        hobby1.setId(null);
        assertThat(hobby1).isNotEqualTo(hobby2);
    }
}
