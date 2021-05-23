package io.changsoft.portfolio.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GalleryMapperTest {

    private GalleryMapper galleryMapper;

    @BeforeEach
    public void setUp() {
        galleryMapper = new GalleryMapperImpl();
    }
}
