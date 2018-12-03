package nc.ki.optisoins.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import nc.ki.optisoins.domain.enumeration.TypeLienAssure;

/**
 * A DTO for the Assure entity.
 */
public class AssureDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private String numeroCafat;

    private LocalDate dateNaissance;

    private TypeLienAssure lienAssure;

    private Long courrielId;

    private Long adresseId;

    private Long telephoneId;

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

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public TypeLienAssure getLienAssure() {
        return lienAssure;
    }

    public void setLienAssure(TypeLienAssure lienAssure) {
        this.lienAssure = lienAssure;
    }

    public Long getCourrielId() {
        return courrielId;
    }

    public void setCourrielId(Long courrielId) {
        this.courrielId = courrielId;
    }

    public Long getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(Long adresseId) {
        this.adresseId = adresseId;
    }

    public Long getTelephoneId() {
        return telephoneId;
    }

    public void setTelephoneId(Long telephoneId) {
        this.telephoneId = telephoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssureDTO assureDTO = (AssureDTO) o;
        if (assureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssureDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", numeroCafat='" + getNumeroCafat() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lienAssure='" + getLienAssure() + "'" +
            ", courriel=" + getCourrielId() +
            ", adresse=" + getAdresseId() +
            ", telephone=" + getTelephoneId() +
            "}";
    }
}
