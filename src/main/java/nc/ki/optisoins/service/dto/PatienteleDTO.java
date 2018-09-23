package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Patientele entity.
 */
public class PatienteleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String description;

    private Long orthophonisteId;

    private Long titulaireId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrthophonisteId() {
        return orthophonisteId;
    }

    public void setOrthophonisteId(Long orthophonisteId) {
        this.orthophonisteId = orthophonisteId;
    }

    public Long getTitulaireId() {
        return titulaireId;
    }

    public void setTitulaireId(Long orthophonisteId) {
        this.titulaireId = orthophonisteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatienteleDTO patienteleDTO = (PatienteleDTO) o;
        if (patienteleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patienteleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatienteleDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", orthophoniste=" + getOrthophonisteId() +
            ", titulaire=" + getTitulaireId() +
            "}";
    }
}
