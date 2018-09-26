package nc.ki.optisoins.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the FeuilleSoins entity.
 */
public class FeuilleSoinsDTO implements Serializable {

    private Long id;

    private Long priseEnChargeId;

    private Long etatRecapitulatifId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPriseEnChargeId() {
        return priseEnChargeId;
    }

    public void setPriseEnChargeId(Long priseEnChargeId) {
        this.priseEnChargeId = priseEnChargeId;
    }

    public Long getEtatRecapitulatifId() {
        return etatRecapitulatifId;
    }

    public void setEtatRecapitulatifId(Long etatRecapitulatifId) {
        this.etatRecapitulatifId = etatRecapitulatifId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeuilleSoinsDTO feuilleSoinsDTO = (FeuilleSoinsDTO) o;
        if (feuilleSoinsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feuilleSoinsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeuilleSoinsDTO{" +
            "id=" + getId() +
            ", priseEnCharge=" + getPriseEnChargeId() +
            ", etatRecapitulatif=" + getEtatRecapitulatifId() +
            "}";
    }
}
