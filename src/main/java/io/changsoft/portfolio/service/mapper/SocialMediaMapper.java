package io.changsoft.portfolio.service.mapper;

import io.changsoft.portfolio.domain.*;
import io.changsoft.portfolio.service.dto.SocialMediaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SocialMedia} and its DTO {@link SocialMediaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SocialMediaMapper extends EntityMapper<SocialMediaDTO, SocialMedia> {}
