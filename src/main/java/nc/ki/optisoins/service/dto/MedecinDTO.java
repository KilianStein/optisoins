package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Medecin entity.
 */
public class MedecinDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String identifiant;

    private Set<AdresseDTO> adresses = new HashSet<>();

    private Set<CourrielDTO> courriels = new HashSet<>();

    private Set<TelephoneDTO> telephones = new HashSet<>();

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

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Set<AdresseDTO> getAdresses() {
        return adresses;
    }

    public void setAdresses(Set<AdresseDTO> adresses) {
        this.adresses = adresses;
    }

    public Set<CourrielDTO> getCourriels() {
        return courriels;
    }

    public void setCourriels(Set<CourrielDTO> courriels) {
        this.courriels = courriels;
    }

    public Set<TelephoneDTO> getTelephones() {
        return telephones;
    }

    public void setTelephones(Set<TelephoneDTO> telephones) {
        this.telephones = telephones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MedecinDTO medecinDTO = (MedecinDTO) o;
        if (medecinDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medecinDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedecinDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", identifiant='" + getIdentifiant() + "'" +
            "}";
    }
}
