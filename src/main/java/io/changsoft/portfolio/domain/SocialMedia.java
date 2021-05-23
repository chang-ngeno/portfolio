package io.changsoft.portfolio.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SocialMedia.
 */
@Entity
@Table(name = "social_media")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SocialMedia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "url_link", nullable = false)
    private String urlLink;

    @NotNull
    @Column(name = "published", nullable = false)
    private Boolean published;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SocialMedia id(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public SocialMedia username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlLink() {
        return this.urlLink;
    }

    public SocialMedia urlLink(String urlLink) {
        this.urlLink = urlLink;
        return this;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public Boolean getPublished() {
        return this.published;
    }

    public SocialMedia published(Boolean published) {
        this.published = published;
        return this;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialMedia)) {
            return false;
        }
        return id != null && id.equals(((SocialMedia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocialMedia{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", urlLink='" + getUrlLink() + "'" +
            ", published='" + getPublished() + "'" +
            "}";
    }
}
