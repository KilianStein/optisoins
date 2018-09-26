package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CompteBancaire entity.
 */
public class CompteBancaireDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeroCompte;

    @NotNull
    private String nomBanque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getNomBanque() {
        return nomBanque;
    }

    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompteBancaireDTO compteBancaireDTO = (CompteBancaireDTO) o;
        if (compteBancaireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compteBancaireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompteBancaireDTO{" +
            "id=" + getId() +
            ", numeroCompte='" + getNumeroCompte() + "'" +
            ", nomBanque='" + getNomBanque() + "'" +
            "}";
    }
}
