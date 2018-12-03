package nc.ki.optisoins.service.dto;

import java.io.Serializable;
import java.util.Objects;
import nc.ki.optisoins.domain.enumeration.TypePriseEnCharge;

/**
 * A DTO for the EtatRecapitulatif entity.
 */
public class EtatRecapitulatifDTO implements Serializable {

    private Long id;

    private TypePriseEnCharge type;

    private Long orthophonisteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePriseEnCharge getType() {
        return type;
    }

    public void setType(TypePriseEnCharge type) {
        this.type = type;
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

        EtatRecapitulatifDTO etatRecapitulatifDTO = (EtatRecapitulatifDTO) o;
        if (etatRecapitulatifDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etatRecapitulatifDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtatRecapitulatifDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", orthophoniste=" + getOrthophonisteId() +
            "}";
    }
}
