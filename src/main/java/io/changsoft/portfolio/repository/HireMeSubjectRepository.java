package io.changsoft.portfolio.repository;

import io.changsoft.portfolio.domain.HireMeSubject;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HireMeSubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HireMeSubjectRepository extends JpaRepository<HireMeSubject, Long> {}
