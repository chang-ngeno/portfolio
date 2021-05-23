package io.changsoft.portfolio.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocialMediaMapperTest {

    private SocialMediaMapper socialMediaMapper;

    @BeforeEach
    public void setUp() {
        socialMediaMapper = new SocialMediaMapperImpl();
    }
}
