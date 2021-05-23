package io.changsoft.portfolio.service.mapper;

import io.changsoft.portfolio.domain.*;
import io.changsoft.portfolio.service.dto.WorkExperienceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkExperience} and its DTO {@link WorkExperienceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkExperienceMapper extends EntityMapper<WorkExperienceDTO, WorkExperience> {}
