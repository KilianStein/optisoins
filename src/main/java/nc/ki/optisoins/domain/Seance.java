package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import nc.ki.optisoins.domain.enumeration.EtatSeance;

/**
 * A Seance.
 */
@Entity
@Table(name = "seance")
public class Seance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private ZonedDateTime dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private ZonedDateTime dateFin;

    @Column(name = "origine")
    private String origine;

    @Column(name = "domicile")
    private Boolean domicile;

    @Column(name = "ticket_moderateur")
    private Boolean ticketModerateur;

    @Column(name = "bilan")
    private Boolean bilan;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatSeance etat;

    @Column(name = "commentaire")
    private String commentaire;

    @OneToOne    @JoinColumn(unique = true)
    private Orthophoniste orthophoniste;

    @ManyToOne
    @JsonIgnoreProperties("seances")
    private PriseEnCharge priseEnCharge;

    @ManyToOne
    @JsonIgnoreProperties("actes")
    private FeuilleSoins feuilleSoins;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public Seance dateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public Seance dateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getOrigine() {
        return origine;
    }

    public Seance origine(String origine) {
        this.origine = origine;
        return this;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public Boolean isDomicile() {
        return domicile;
    }

    public Seance domicile(Boolean domicile) {
        this.domicile = domicile;
        return this;
    }

    public void setDomicile(Boolean domicile) {
        this.domicile = domicile;
    }

    public Boolean isTicketModerateur() {
        return ticketModerateur;
    }

    public Seance ticketModerateur(Boolean ticketModerateur) {
        this.ticketModerateur = ticketModerateur;
        return this;
    }

    public void setTicketModerateur(Boolean ticketModerateur) {
        this.ticketModerateur = ticketModerateur;
    }

    public Boolean isBilan() {
        return bilan;
    }

    public Seance bilan(Boolean bilan) {
        this.bilan = bilan;
        return this;
    }

    public void setBilan(Boolean bilan) {
        this.bilan = bilan;
    }

    public EtatSeance getEtat() {
        return etat;
    }

    public Seance etat(EtatSeance etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatSeance etat) {
        this.etat = etat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Seance commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Orthophoniste getOrthophoniste() {
        return orthophoniste;
    }

    public Seance orthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
        return this;
    }

    public void setOrthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
    }

    public PriseEnCharge getPriseEnCharge() {
        return priseEnCharge;
    }

    public Seance priseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
        return this;
    }

    public void setPriseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
    }

    public FeuilleSoins getFeuilleSoins() {
        return feuilleSoins;
    }

    public Seance feuilleSoins(FeuilleSoins feuilleSoins) {
        this.feuilleSoins = feuilleSoins;
        return this;
    }

    public void setFeuilleSoins(FeuilleSoins feuilleSoins) {
        this.feuilleSoins = feuilleSoins;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seance seance = (Seance) o;
        if (seance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Seance{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", origine='" + getOrigine() + "'" +
            ", domicile='" + isDomicile() + "'" +
            ", ticketModerateur='" + isTicketModerateur() + "'" +
            ", bilan='" + isBilan() + "'" +
            ", etat='" + getEtat() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
