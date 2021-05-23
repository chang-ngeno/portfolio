package io.changsoft.portfolio.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.changsoft.portfolio.domain.Hobby} entity.
 */
public class HobbyDTO implements Serializable {

    private Long id;

    @NotNull
    private String slug;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HobbyDTO)) {
            return false;
        }

        HobbyDTO hobbyDTO = (HobbyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hobbyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HobbyDTO{" +
            "id=" + getId() +
            ", slug='" + getSlug() + "'" +
            "}";
    }
}
