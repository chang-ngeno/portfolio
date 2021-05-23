package io.changsoft.portfolio.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.changsoft.portfolio.domain.Gallery} entity.
 */
public class GalleryDTO implements Serializable {

    private Long id;

    @NotNull
    private String galleryName;

    @Lob
    private String slug;

    @Lob
    private byte[] photo;

    private String photoContentType;
    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GalleryDTO)) {
            return false;
        }

        GalleryDTO galleryDTO = (GalleryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, galleryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GalleryDTO{" +
            "id=" + getId() +
            ", galleryName='" + getGalleryName() + "'" +
            ", slug='" + getSlug() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", project=" + getProject() +
            "}";
    }
}
