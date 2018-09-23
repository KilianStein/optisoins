package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FeuilleSoins.
 */
@Entity
@Table(name = "feuille_soins")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FeuilleSoins implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("feuillesSoins")
    private PriseEnCharge priseEnCharge;

    @OneToMany(mappedBy = "feuilleSoins")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Seance> actes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("feuilleSoins")
    private EtatRecapitulatif etatRecapitulatif;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriseEnCharge getPriseEnCharge() {
        return priseEnCharge;
    }

    public FeuilleSoins priseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
        return this;
    }

    public void setPriseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
    }

    public Set<Seance> getActes() {
        return actes;
    }

    public FeuilleSoins actes(Set<Seance> seances) {
        this.actes = seances;
        return this;
    }

    public FeuilleSoins addActes(Seance seance) {
        this.actes.add(seance);
        seance.setFeuilleSoins(this);
        return this;
    }

    public FeuilleSoins removeActes(Seance seance) {
        this.actes.remove(seance);
        seance.setFeuilleSoins(null);
        return this;
    }

    public void setActes(Set<Seance> seances) {
        this.actes = seances;
    }

    public EtatRecapitulatif getEtatRecapitulatif() {
        return etatRecapitulatif;
    }

    public FeuilleSoins etatRecapitulatif(EtatRecapitulatif etatRecapitulatif) {
        this.etatRecapitulatif = etatRecapitulatif;
        return this;
    }

    public void setEtatRecapitulatif(EtatRecapitulatif etatRecapitulatif) {
        this.etatRecapitulatif = etatRecapitulatif;
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
        FeuilleSoins feuilleSoins = (FeuilleSoins) o;
        if (feuilleSoins.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feuilleSoins.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeuilleSoins{" +
            "id=" + getId() +
            "}";
    }
}
