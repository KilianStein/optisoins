package nc.ki.optisoins.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import nc.ki.optisoins.domain.enumeration.SituationPatient;

/**
 * A DTO for the Patient entity.
 */
public class PatientDTO implements Serializable {

    private Long id;

    @NotNull
    private String prenom;

    @NotNull
    private String nom;

    private String numeroCafat;

    @NotNull
    private LocalDate dateNaissance;

    private String employeur;

    @NotNull
    private SituationPatient situation;

    private Long patienteleId;

    private Long assureId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumeroCafat() {
        return numeroCafat;
    }

    public void setNumeroCafat(String numeroCafat) {
        this.numeroCafat = numeroCafat;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmployeur() {
        return employeur;
    }

    public void setEmployeur(String employeur) {
        this.employeur = employeur;
    }

    public SituationPatient getSituation() {
        return situation;
    }

    public void setSituation(SituationPatient situation) {
        this.situation = situation;
    }

    public Long getPatienteleId() {
        return patienteleId;
    }

    public void setPatienteleId(Long patienteleId) {
        this.patienteleId = patienteleId;
    }

    public Long getAssureId() {
        return assureId;
    }

    public void setAssureId(Long assureId) {
        this.assureId = assureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatientDTO patientDTO = (PatientDTO) o;
        if (patientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatientDTO{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", numeroCafat='" + getNumeroCafat() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", employeur='" + getEmployeur() + "'" +
            ", situation='" + getSituation() + "'" +
            ", patientele=" + getPatienteleId() +
            ", assure=" + getAssureId() +
            "}";
    }
}
