package io.changsoft.portfolio.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkExperienceMapperTest {

    private WorkExperienceMapper workExperienceMapper;

    @BeforeEach
    public void setUp() {
        workExperienceMapper = new WorkExperienceMapperImpl();
    }
}
