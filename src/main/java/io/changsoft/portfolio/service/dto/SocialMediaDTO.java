package io.changsoft.portfolio.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.changsoft.portfolio.domain.SocialMedia} entity.
 */
public class SocialMediaDTO implements Serializable {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String urlLink;

    @NotNull
    private Boolean published;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialMediaDTO)) {
            return false;
        }

        SocialMediaDTO socialMediaDTO = (SocialMediaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, socialMediaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocialMediaDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", urlLink='" + getUrlLink() + "'" +
            ", published='" + getPublished() + "'" +
            "}";
    }
}
