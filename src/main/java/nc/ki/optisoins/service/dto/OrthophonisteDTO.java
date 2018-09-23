package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    private Set<TelephoneDTO> telephones = new HashSet<>();

    private Set<AdresseDTO> adresses = new HashSet<>();

    private Set<CourrielDTO> courriels = new HashSet<>();

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

    public Set<TelephoneDTO> getTelephones() {
        return telephones;
    }

    public void setTelephones(Set<TelephoneDTO> telephones) {
        this.telephones = telephones;
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
