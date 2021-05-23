package io.changsoft.portfolio.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.changsoft.portfolio.domain.HireMeSubject} entity.
 */
public class HireMeSubjectDTO implements Serializable {

    private Long id;

    @NotNull
    private String subject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HireMeSubjectDTO)) {
            return false;
        }

        HireMeSubjectDTO hireMeSubjectDTO = (HireMeSubjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hireMeSubjectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HireMeSubjectDTO{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            "}";
    }
}
