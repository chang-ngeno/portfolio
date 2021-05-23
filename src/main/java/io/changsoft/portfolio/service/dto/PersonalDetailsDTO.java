package io.changsoft.portfolio.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.changsoft.portfolio.domain.PersonalDetails} entity.
 */
public class PersonalDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String names;

    @Lob
    private String slug;

    @NotNull
    private String email;

    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalDetailsDTO)) {
            return false;
        }

        PersonalDetailsDTO personalDetailsDTO = (PersonalDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personalDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalDetailsDTO{" +
            "id=" + getId() +
            ", names='" + getNames() + "'" +
            ", slug='" + getSlug() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
