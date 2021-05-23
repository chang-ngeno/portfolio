package io.changsoft.portfolio.service.mapper;

import io.changsoft.portfolio.domain.*;
import io.changsoft.portfolio.service.dto.HireMeSubjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HireMeSubject} and its DTO {@link HireMeSubjectDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HireMeSubjectMapper extends EntityMapper<HireMeSubjectDTO, HireMeSubject> {}
