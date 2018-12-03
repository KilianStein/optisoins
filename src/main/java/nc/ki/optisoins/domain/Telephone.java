package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import nc.ki.optisoins.domain.enumeration.TypeTelephone;

/**
 * A Telephone.
 */
@Entity
@Table(name = "telephone")
public class Telephone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 6)
    @Column(name = "numero_telephone", nullable = false)
    private String numeroTelephone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypeTelephone type;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JsonIgnoreProperties("telephones")
    private Orthophoniste orthophoniste;

    @ManyToOne
    @JsonIgnoreProperties("telephones")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("telephones")
    private Medecin medecin;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public Telephone numeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
        return this;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public TypeTelephone getType() {
        return type;
    }

    public Telephone type(TypeTelephone type) {
        this.type = type;
        return this;
    }

    public void setType(TypeTelephone type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Telephone commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Orthophoniste getOrthophoniste() {
        return orthophoniste;
    }

    public Telephone orthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
        return this;
    }

    public void setOrthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
    }

    public Patient getPatient() {
        return patient;
    }

    public Telephone patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public Telephone medecin(Medecin medecin) {
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
        Telephone telephone = (Telephone) o;
        if (telephone.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telephone.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Telephone{" +
            "id=" + getId() +
            ", numeroTelephone='" + getNumeroTelephone() + "'" +
            ", type='" + getType() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
