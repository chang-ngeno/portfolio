package io.changsoft.portfolio.repository;

import io.changsoft.portfolio.domain.Hobby;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Hobby entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {}
