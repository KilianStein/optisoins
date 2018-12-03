package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import nc.ki.optisoins.domain.enumeration.TypePriseEnCharge;

/**
 * A PriseEnCharge.
 */
@Entity
@Table(name = "prise_en_charge")
public class PriseEnCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypePriseEnCharge type;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "accident")
    private LocalDate accident;

    @Column(name = "nom_tier_implique")
    private String nomTierImplique;

    @ManyToOne
    @JsonIgnoreProperties("prisesEnCharges")
    private Patient patient;

    @OneToMany(mappedBy = "priseEnCharge")
    private Set<Ordonnance> ordonnances = new HashSet<>();
    @OneToMany(mappedBy = "priseEnCharge")
    private Set<Seance> seances = new HashSet<>();
    @OneToMany(mappedBy = "priseEnCharge")
    private Set<FeuilleSoins> feuillesSoins = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypePriseEnCharge getType() {
        return type;
    }

    public PriseEnCharge type(TypePriseEnCharge type) {
        this.type = type;
        return this;
    }

    public void setType(TypePriseEnCharge type) {
        this.type = type;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public PriseEnCharge dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public PriseEnCharge dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getAccident() {
        return accident;
    }

    public PriseEnCharge accident(LocalDate accident) {
        this.accident = accident;
        return this;
    }

    public void setAccident(LocalDate accident) {
        this.accident = accident;
    }

    public String getNomTierImplique() {
        return nomTierImplique;
    }

    public PriseEnCharge nomTierImplique(String nomTierImplique) {
        this.nomTierImplique = nomTierImplique;
        return this;
    }

    public void setNomTierImplique(String nomTierImplique) {
        this.nomTierImplique = nomTierImplique;
    }

    public Patient getPatient() {
        return patient;
    }

    public PriseEnCharge patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<Ordonnance> getOrdonnances() {
        return ordonnances;
    }

    public PriseEnCharge ordonnances(Set<Ordonnance> ordonnances) {
        this.ordonnances = ordonnances;
        return this;
    }

    public PriseEnCharge addOrdonnances(Ordonnance ordonnance) {
        this.ordonnances.add(ordonnance);
        ordonnance.setPriseEnCharge(this);
        return this;
    }

    public PriseEnCharge removeOrdonnances(Ordonnance ordonnance) {
        this.ordonnances.remove(ordonnance);
        ordonnance.setPriseEnCharge(null);
        return this;
    }

    public void setOrdonnances(Set<Ordonnance> ordonnances) {
        this.ordonnances = ordonnances;
    }

    public Set<Seance> getSeances() {
        return seances;
    }

    public PriseEnCharge seances(Set<Seance> seances) {
        this.seances = seances;
        return this;
    }

    public PriseEnCharge addSeances(Seance seance) {
        this.seances.add(seance);
        seance.setPriseEnCharge(this);
        return this;
    }

    public PriseEnCharge removeSeances(Seance seance) {
        this.seances.remove(seance);
        seance.setPriseEnCharge(null);
        return this;
    }

    public void setSeances(Set<Seance> seances) {
        this.seances = seances;
    }

    public Set<FeuilleSoins> getFeuillesSoins() {
        return feuillesSoins;
    }

    public PriseEnCharge feuillesSoins(Set<FeuilleSoins> feuilleSoins) {
        this.feuillesSoins = feuilleSoins;
        return this;
    }

    public PriseEnCharge addFeuillesSoins(FeuilleSoins feuilleSoins) {
        this.feuillesSoins.add(feuilleSoins);
        feuilleSoins.setPriseEnCharge(this);
        return this;
    }

    public PriseEnCharge removeFeuillesSoins(FeuilleSoins feuilleSoins) {
        this.feuillesSoins.remove(feuilleSoins);
        feuilleSoins.setPriseEnCharge(null);
        return this;
    }

    public void setFeuillesSoins(Set<FeuilleSoins> feuilleSoins) {
        this.feuillesSoins = feuilleSoins;
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
        PriseEnCharge priseEnCharge = (PriseEnCharge) o;
        if (priseEnCharge.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priseEnCharge.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriseEnCharge{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", accident='" + getAccident() + "'" +
            ", nomTierImplique='" + getNomTierImplique() + "'" +
            "}";
    }
}
