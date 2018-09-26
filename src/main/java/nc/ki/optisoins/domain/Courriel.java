package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import nc.ki.optisoins.domain.enumeration.TypeCourriel;

/**
 * A Courriel.
 */
@Entity
@Table(name = "courriel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Courriel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypeCourriel type;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JsonIgnoreProperties("courriels")
    private Orthophoniste orthophoniste;

    @ManyToOne
    @JsonIgnoreProperties("courriels")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("courriels")
    private Medecin medecin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Courriel email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypeCourriel getType() {
        return type;
    }

    public Courriel type(TypeCourriel type) {
        this.type = type;
        return this;
    }

    public void setType(TypeCourriel type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Courriel commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Orthophoniste getOrthophoniste() {
        return orthophoniste;
    }

    public Courriel orthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
        return this;
    }

    public void setOrthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
    }

    public Patient getPatient() {
        return patient;
    }

    public Courriel patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public Courriel medecin(Medecin medecin) {
        this.medecin = medecin;
        return this;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
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
        Courriel courriel = (Courriel) o;
        if (courriel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courriel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Courriel{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", type='" + getType() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
