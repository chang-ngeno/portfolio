package io.changsoft.portfolio.service.mapper;

import io.changsoft.portfolio.domain.*;
import io.changsoft.portfolio.service.dto.HobbyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hobby} and its DTO {@link HobbyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HobbyMapper extends EntityMapper<HobbyDTO, Hobby> {}
