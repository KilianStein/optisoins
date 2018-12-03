package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the DemandeEntentePrealable entity.
 */
public class DemandeEntentePrealableDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeroACP;

    @NotNull
    private Integer nombreSeances;

    private Long ordonnanceId;

    private Long amoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroACP() {
        return numeroACP;
    }

    public void setNumeroACP(String numeroACP) {
        this.numeroACP = numeroACP;
    }

    public Integer getNombreSeances() {
        return nombreSeances;
    }

    public void setNombreSeances(Integer nombreSeances) {
        this.nombreSeances = nombreSeances;
    }

    public Long getOrdonnanceId() {
        return ordonnanceId;
    }

    public void setOrdonnanceId(Long ordonnanceId) {
        this.ordonnanceId = ordonnanceId;
    }

    public Long getAmoId() {
        return amoId;
    }

    public void setAmoId(Long amoId) {
        this.amoId = amoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DemandeEntentePrealableDTO demandeEntentePrealableDTO = (DemandeEntentePrealableDTO) o;
        if (demandeEntentePrealableDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), demandeEntentePrealableDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DemandeEntentePrealableDTO{" +
            "id=" + getId() +
            ", numeroACP='" + getNumeroACP() + "'" +
            ", nombreSeances=" + getNombreSeances() +
            ", ordonnance=" + getOrdonnanceId() +
            ", amo=" + getAmoId() +
            "}";
    }
}
