package nc.ki.optisoins.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CarteAideMedicale entity.
 */
public class CarteAideMedicaleDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate dateDebutValidite;

    @NotNull
    private LocalDate dateFinValidite;

    @NotNull
    private String numero;

    private Long assureId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebutValidite() {
        return dateDebutValidite;
    }

    public void setDateDebutValidite(LocalDate dateDebutValidite) {
        this.dateDebutValidite = dateDebutValidite;
    }

    public LocalDate getDateFinValidite() {
        return dateFinValidite;
    }

    public void setDateFinValidite(LocalDate dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

        CarteAideMedicaleDTO carteAideMedicaleDTO = (CarteAideMedicaleDTO) o;
        if (carteAideMedicaleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carteAideMedicaleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarteAideMedicaleDTO{" +
            "id=" + getId() +
            ", dateDebutValidite='" + getDateDebutValidite() + "'" +
            ", dateFinValidite='" + getDateFinValidite() + "'" +
            ", numero='" + getNumero() + "'" +
            ", assure=" + getAssureId() +
            "}";
    }
}
