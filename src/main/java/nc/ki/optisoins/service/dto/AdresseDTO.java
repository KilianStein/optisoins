package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import nc.ki.optisoins.domain.enumeration.TypeAdresse;

/**
 * A DTO for the Adresse entity.
 */
public class AdresseDTO implements Serializable {

    private Long id;

    private String appartement;

    private String batiment;

    private String rue;

    private String codePostal;

    private String boitePostale;

    private String commune;

    @NotNull
    private TypeAdresse type;

    private String commentaire;

    private Boolean nonValide;

    private Long orthophonisteId;

    private Long patientId;

    private Long medecinId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppartement() {
        return appartement;
    }

    public void setAppartement(String appartement) {
        this.appartement = appartement;
    }

    public String getBatiment() {
        return batiment;
    }

    public void setBatiment(String batiment) {
        this.batiment = batiment;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getBoitePostale() {
        return boitePostale;
    }

    public void setBoitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public TypeAdresse getType() {
        return type;
    }

    public void setType(TypeAdresse type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Boolean isNonValide() {
        return nonValide;
    }

    public void setNonValide(Boolean nonValide) {
        this.nonValide = nonValide;
    }

    public Long getOrthophonisteId() {
        return orthophonisteId;
    }

    public void setOrthophonisteId(Long orthophonisteId) {
        this.orthophonisteId = orthophonisteId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdresseDTO adresseDTO = (AdresseDTO) o;
        if (adresseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adresseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdresseDTO{" +
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
            ", orthophoniste=" + getOrthophonisteId() +
            ", patient=" + getPatientId() +
            ", medecin=" + getMedecinId() +
            "}";
    }
}
