package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Orthophoniste entity.
 */
public class OrthophonisteDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    private String numeroCafat;

    private String numeroRidet;

    private Long compteBancaireId;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroCafat() {
        return numeroCafat;
    }

    public void setNumeroCafat(String numeroCafat) {
        this.numeroCafat = numeroCafat;
    }

    public String getNumeroRidet() {
        return numeroRidet;
    }

    public void setNumeroRidet(String numeroRidet) {
        this.numeroRidet = numeroRidet;
    }

    public Long getCompteBancaireId() {
        return compteBancaireId;
    }

    public void setCompteBancaireId(Long compteBancaireId) {
        this.compteBancaireId = compteBancaireId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrthophonisteDTO orthophonisteDTO = (OrthophonisteDTO) o;
        if (orthophonisteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orthophonisteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrthophonisteDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", numeroCafat='" + getNumeroCafat() + "'" +
            ", numeroRidet='" + getNumeroRidet() + "'" +
            ", compteBancaire=" + getCompteBancaireId() +
            "}";
    }
}
