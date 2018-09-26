package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import nc.ki.optisoins.domain.enumeration.TypeMutuelle;

/**
 * A DTO for the Mutuelle entity.
 */
public class MutuelleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String numero;

    @NotNull
    private TypeMutuelle type;

    private Long assureId;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TypeMutuelle getType() {
        return type;
    }

    public void setType(TypeMutuelle type) {
        this.type = type;
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

        MutuelleDTO mutuelleDTO = (MutuelleDTO) o;
        if (mutuelleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mutuelleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MutuelleDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", numero='" + getNumero() + "'" +
            ", type='" + getType() + "'" +
            ", assure=" + getAssureId() +
            "}";
    }
}
