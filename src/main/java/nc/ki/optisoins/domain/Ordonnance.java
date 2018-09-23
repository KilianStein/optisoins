package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ordonnance.
 */
@Entity
@Table(name = "ordonnance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ordonnance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_prescription", nullable = false)
    private LocalDate datePrescription;

    @NotNull
    @Column(name = "nombre_seances", nullable = false)
    private Integer nombreSeances;

    @OneToOne
    @JoinColumn(unique = true)
    private Medecin medecin;

    @OneToMany(mappedBy = "ordonnance")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DemandeEntentePrealable> demandeEntentePrealables = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("ordonnances")
    private PriseEnCharge priseEnCharge;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatePrescription() {
        return datePrescription;
    }

    public Ordonnance datePrescription(LocalDate datePrescription) {
        this.datePrescription = datePrescription;
        return this;
    }

    public void setDatePrescription(LocalDate datePrescription) {
        this.datePrescription = datePrescription;
    }

    public Integer getNombreSeances() {
        return nombreSeances;
    }

    public Ordonnance nombreSeances(Integer nombreSeances) {
        this.nombreSeances = nombreSeances;
        return this;
    }

    public void setNombreSeances(Integer nombreSeances) {
        this.nombreSeances = nombreSeances;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public Ordonnance medecin(Medecin medecin) {
        this.medecin = medecin;
        return this;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Set<DemandeEntentePrealable> getDemandeEntentePrealables() {
        return demandeEntentePrealables;
    }

    public Ordonnance demandeEntentePrealables(Set<DemandeEntentePrealable> demandeEntentePrealables) {
        this.demandeEntentePrealables = demandeEntentePrealables;
        return this;
    }

    public Ordonnance addDemandeEntentePrealable(DemandeEntentePrealable demandeEntentePrealable) {
        this.demandeEntentePrealables.add(demandeEntentePrealable);
        demandeEntentePrealable.setOrdonnance(this);
        return this;
    }

    public Ordonnance removeDemandeEntentePrealable(DemandeEntentePrealable demandeEntentePrealable) {
        this.demandeEntentePrealables.remove(demandeEntentePrealable);
        demandeEntentePrealable.setOrdonnance(null);
        return this;
    }

    public void setDemandeEntentePrealables(Set<DemandeEntentePrealable> demandeEntentePrealables) {
        this.demandeEntentePrealables = demandeEntentePrealables;
    }

    public PriseEnCharge getPriseEnCharge() {
        return priseEnCharge;
    }

    public Ordonnance priseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
        return this;
    }

    public void setPriseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
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
        Ordonnance ordonnance = (Ordonnance) o;
        if (ordonnance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordonnance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ordonnance{" +
            "id=" + getId() +
            ", datePrescription='" + getDatePrescription() + "'" +
            ", nombreSeances=" + getNombreSeances() +
            "}";
    }
}
