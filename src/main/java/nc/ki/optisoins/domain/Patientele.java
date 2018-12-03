package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Patientele.
 */
@Entity
@Table(name = "patientele")
public class Patientele implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("patienteles")
    private Orthophoniste orthophoniste;

    @OneToOne    @JoinColumn(unique = true)
    private Orthophoniste titulaire;

    @OneToMany(mappedBy = "patientele")
    private Set<Patient> patients = new HashSet<>();
    @OneToMany(mappedBy = "patientele")
    private Set<Remplacante> remplacantes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Patientele nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Patientele description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Orthophoniste getOrthophoniste() {
        return orthophoniste;
    }

    public Patientele orthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
        return this;
    }

    public void setOrthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
    }

    public Orthophoniste getTitulaire() {
        return titulaire;
    }

    public Patientele titulaire(Orthophoniste orthophoniste) {
        this.titulaire = orthophoniste;
        return this;
    }

    public void setTitulaire(Orthophoniste orthophoniste) {
        this.titulaire = orthophoniste;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public Patientele patients(Set<Patient> patients) {
        this.patients = patients;
        return this;
    }

    public Patientele addPatients(Patient patient) {
        this.patients.add(patient);
        patient.setPatientele(this);
        return this;
    }

    public Patientele removePatients(Patient patient) {
        this.patients.remove(patient);
        patient.setPatientele(null);
        return this;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public Set<Remplacante> getRemplacantes() {
        return remplacantes;
    }

    public Patientele remplacantes(Set<Remplacante> remplacantes) {
        this.remplacantes = remplacantes;
        return this;
    }

    public Patientele addRemplacantes(Remplacante remplacante) {
        this.remplacantes.add(remplacante);
        remplacante.setPatientele(this);
        return this;
    }

    public Patientele removeRemplacantes(Remplacante remplacante) {
        this.remplacantes.remove(remplacante);
        remplacante.setPatientele(null);
        return this;
    }

    public void setRemplacantes(Set<Remplacante> remplacantes) {
        this.remplacantes = remplacantes;
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
        Patientele patientele = (Patientele) o;
        if (patientele.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patientele.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patientele{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
