package nc.ki.optisoins.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import nc.ki.optisoins.domain.enumeration.TypeAdresse;

/**
 * A Adresse.
 */
@Entity
@Table(name = "adresse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Adresse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "appartement")
    private String appartement;

    @Column(name = "batiment")
    private String batiment;

    @Column(name = "rue")
    private String rue;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "boite_postale")
    private String boitePostale;

    @Column(name = "commune")
    private String commune;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypeAdresse type;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "non_valide")
    private Boolean nonValide;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppartement() {
        return appartement;
    }

    public Adresse appartement(String appartement) {
        this.appartement = appartement;
        return this;
    }

    public void setAppartement(String appartement) {
        this.appartement = appartement;
    }

    public String getBatiment() {
        return batiment;
    }

    public Adresse batiment(String batiment) {
        this.batiment = batiment;
        return this;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public String getRue() {
        return rue;
    }

    public Adresse rue(String rue) {
        this.rue = rue;
        return this;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public Adresse codePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getBoitePostale() {
        return boitePostale;
    }

    public Adresse boitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
        return this;
    }

    public void setBoitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
    }

    public String getCommune() {
        return commune;
    }

    public Adresse commune(String commune) {
        this.commune = commune;
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public TypeAdresse getType() {
        return type;
    }

    public Adresse type(TypeAdresse type) {
        this.type = type;
        return this;
    }

    public void setType(TypeAdresse type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Adresse commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean isNonValide() {
        return nonValide;
    }

    public Adresse nonValide(Boolean nonValide) {
        this.nonValide = nonValide;
        return this;
    }

    public void setNonValide(Boolean nonValide) {
        this.nonValide = nonValide;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Adresse adresse = (Adresse) o;
        if (adresse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adresse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Adresse{" +
            "id=" + getId() +
            ", appartement='" + getAppartement() + "'" +
            ", batiment='" + getBatiment() + "'" +
            ", rue='" + getRue() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", boitePostale='" + getBoitePostale() + "'" +
            ", commune='" + getCommune() + "'" +
            ", type='" + getType() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", nonValide='" + isNonValide() + "'" +
            "}";
    }
}
