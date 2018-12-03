package nc.ki.optisoins.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import nc.ki.optisoins.domain.enumeration.TypePriseEnCharge;

/**
 * A DTO for the PriseEnCharge entity.
 */
public class PriseEnChargeDTO implements Serializable {

    private Long id;

    @NotNull
    private TypePriseEnCharge type;

    @NotNull
    private LocalDate dateDebut;

    private LocalDate dateFin;

    private LocalDate accident;

    private String nomTierImplique;

    private Long patientId;

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

    public LocalDate getAccident() {
        return accident;
    }

    public void setAccident(LocalDate accident) {
        this.accident = accident;
    }

    public String getNomTierImplique() {
        return nomTierImplique;
    }

    public void setNomTierImplique(String nomTierImplique) {
        this.nomTierImplique = nomTierImplique;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriseEnChargeDTO priseEnChargeDTO = (PriseEnChargeDTO) o;
        if (priseEnChargeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priseEnChargeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriseEnChargeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", accident='" + getAccident() + "'" +
            ", nomTierImplique='" + getNomTierImplique() + "'" +
            ", patient=" + getPatientId() +
            "}";
    }
}
