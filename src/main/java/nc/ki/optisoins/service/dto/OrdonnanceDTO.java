package nc.ki.optisoins.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Ordonnance entity.
 */
public class OrdonnanceDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate datePrescription;

    @NotNull
    private Integer nombreSeances;

    private Long medecinId;

    private Long priseEnChargeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatePrescription() {
        return datePrescription;
    }

    public void setDatePrescription(LocalDate datePrescription) {
        this.datePrescription = datePrescription;
    }

    public Integer getNombreSeances() {
        return nombreSeances;
    }

    public void setNombreSeances(Integer nombreSeances) {
        this.nombreSeances = nombreSeances;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    public Long getPriseEnChargeId() {
        return priseEnChargeId;
    }

    public void setPriseEnChargeId(Long priseEnChargeId) {
        this.priseEnChargeId = priseEnChargeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdonnanceDTO ordonnanceDTO = (OrdonnanceDTO) o;
        if (ordonnanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordonnanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdonnanceDTO{" +
            "id=" + getId() +
            ", datePrescription='" + getDatePrescription() + "'" +
            ", nombreSeances=" + getNombreSeances() +
            ", medecin=" + getMedecinId() +
            ", priseEnCharge=" + getPriseEnChargeId() +
            "}";
    }
}
