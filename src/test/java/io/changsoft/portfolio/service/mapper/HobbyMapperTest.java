package io.changsoft.portfolio.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HobbyMapperTest {

    private HobbyMapper hobbyMapper;

    @BeforeEach
    public void setUp() {
        hobbyMapper = new HobbyMapperImpl();
    }
}
