package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import nc.ki.optisoins.domain.enumeration.TypeTelephone;

/**
 * A DTO for the Telephone entity.
 */
public class TelephoneDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 6)
    private String numeroTelephone;

    @NotNull
    private TypeTelephone type;

    private String commentaire;

    private Long orthophonisteId;

    private Long patientId;

    private Long medecinId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public TypeTelephone getType() {
        return type;
    }

    public void setType(TypeTelephone type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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

        TelephoneDTO telephoneDTO = (TelephoneDTO) o;
        if (telephoneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), telephoneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TelephoneDTO{" +
            "id=" + getId() +
            ", numeroTelephone='" + getNumeroTelephone() + "'" +
            ", type='" + getType() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", orthophoniste=" + getOrthophonisteId() +
            ", patient=" + getPatientId() +
            ", medecin=" + getMedecinId() +
            "}";
    }
}
