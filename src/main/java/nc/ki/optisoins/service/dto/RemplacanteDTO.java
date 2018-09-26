package nc.ki.optisoins.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Remplacante entity.
 */
public class RemplacanteDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateDebut;

    @NotNull
    private LocalDate dateFin;

    private Long patienteleId;

    private Long orthophonisteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Long getPatienteleId() {
        return patienteleId;
    }

    public void setPatienteleId(Long patienteleId) {
        this.patienteleId = patienteleId;
    }

    public Long getOrthophonisteId() {
        return orthophonisteId;
    }

    public void setOrthophonisteId(Long orthophonisteId) {
        this.orthophonisteId = orthophonisteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RemplacanteDTO remplacanteDTO = (RemplacanteDTO) o;
        if (remplacanteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), remplacanteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RemplacanteDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", patientele=" + getPatienteleId() +
            ", orthophoniste=" + getOrthophonisteId() +
            "}";
    }
}
