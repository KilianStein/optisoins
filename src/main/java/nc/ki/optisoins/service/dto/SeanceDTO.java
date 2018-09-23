package nc.ki.optisoins.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import nc.ki.optisoins.domain.enumeration.EtatSeance;

/**
 * A DTO for the Seance entity.
 */
public class SeanceDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime dateDebut;

    @NotNull
    private ZonedDateTime dateFin;

    private String origine;

    private Boolean domicile;

    private Boolean ticketModerateur;

    private Boolean bilan;

    private EtatSeance etat;

    private String commentaire;

    private Long orthophonisteId;

    private Long priseEnChargeId;

    private Long feuilleSoinsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Boolean isDomicile() {
        return domicile;
    }

    public void setDomicile(Boolean domicile) {
        this.domicile = domicile;
    }

    public Boolean isTicketModerateur() {
        return ticketModerateur;
    }

    public void setTicketModerateur(Boolean ticketModerateur) {
        this.ticketModerateur = ticketModerateur;
    }

    public Boolean isBilan() {
        return bilan;
    }

    public void setBilan(Boolean bilan) {
        this.bilan = bilan;
    }

    public EtatSeance getEtat() {
        return etat;
    }

    public void setEtat(EtatSeance etat) {
        this.etat = etat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Long getOrthophonisteId() {
        return orthophonisteId;
    }

    public void setOrthophonisteId(Long orthophonisteId) {
        this.orthophonisteId = orthophonisteId;
    }

    public Long getPriseEnChargeId() {
        return priseEnChargeId;
    }

    public void setPriseEnChargeId(Long priseEnChargeId) {
        this.priseEnChargeId = priseEnChargeId;
    }

    public Long getFeuilleSoinsId() {
        return feuilleSoinsId;
    }

    public void setFeuilleSoinsId(Long feuilleSoinsId) {
        this.feuilleSoinsId = feuilleSoinsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeanceDTO seanceDTO = (SeanceDTO) o;
        if (seanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeanceDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", origine='" + getOrigine() + "'" +
            ", domicile='" + isDomicile() + "'" +
            ", ticketModerateur='" + isTicketModerateur() + "'" +
            ", bilan='" + isBilan() + "'" +
            ", etat='" + getEtat() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", orthophoniste=" + getOrthophonisteId() +
            ", priseEnCharge=" + getPriseEnChargeId() +
            ", feuilleSoins=" + getFeuilleSoinsId() +
            "}";
    }
}
