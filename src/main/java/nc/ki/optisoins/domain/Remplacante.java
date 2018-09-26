package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Remplacante.
 */
@Entity
@Table(name = "remplacante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Remplacante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @ManyToOne
    @JsonIgnoreProperties("remplacantes")
    private Patientele patientele;

    @OneToOne
    @JoinColumn(unique = true)
    private Orthophoniste orthophoniste;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Remplacante dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Remplacante dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Patientele getPatientele() {
        return patientele;
    }

    public Remplacante patientele(Patientele patientele) {
        this.patientele = patientele;
        return this;
    }

    public void setPatientele(Patientele patientele) {
        this.patientele = patientele;
    }

    public Orthophoniste getOrthophoniste() {
        return orthophoniste;
    }

    public Remplacante orthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
        return this;
    }

    public void setOrthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
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
        Remplacante remplacante = (Remplacante) o;
        if (remplacante.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), remplacante.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Remplacante{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
