package io.changsoft.portfolio.service.mapper;

import io.changsoft.portfolio.domain.*;
import io.changsoft.portfolio.service.dto.GalleryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gallery} and its DTO {@link GalleryDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProjectMapper.class })
public interface GalleryMapper extends EntityMapper<GalleryDTO, Gallery> {
    @Mapping(target = "project", source = "project", qualifiedByName = "title")
    GalleryDTO toDto(Gallery s);
}
