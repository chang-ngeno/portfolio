package io.changsoft.portfolio.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.changsoft.portfolio.domain.WorkExperience} entity.
 */
public class WorkExperienceDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String employer;

    @NotNull
    private Instant startDate;

    private Instant endDate;

    @Lob
    private String roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkExperienceDTO)) {
            return false;
        }

        WorkExperienceDTO workExperienceDTO = (WorkExperienceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workExperienceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkExperienceDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", employer='" + getEmployer() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", roles='" + getRoles() + "'" +
            "}";
    }
}
