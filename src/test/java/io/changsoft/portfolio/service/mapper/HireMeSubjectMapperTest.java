package io.changsoft.portfolio.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HireMeSubjectMapperTest {

    private HireMeSubjectMapper hireMeSubjectMapper;

    @BeforeEach
    public void setUp() {
        hireMeSubjectMapper = new HireMeSubjectMapperImpl();
    }
}
