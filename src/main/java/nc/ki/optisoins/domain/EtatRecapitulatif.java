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

import nc.ki.optisoins.domain.enumeration.TypePriseEnCharge;

/**
 * A EtatRecapitulatif.
 */
@Entity
@Table(name = "etat_recapitulatif")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EtatRecapitulatif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TypePriseEnCharge type;

    @ManyToOne
    @JsonIgnoreProperties("etatsRecapitulatifs")
    private Orthophoniste orthophoniste;

    @OneToMany(mappedBy = "etatRecapitulatif")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FeuilleSoins> feuilleSoins = new HashSet<>();

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

    public EtatRecapitulatif type(TypePriseEnCharge type) {
        this.type = type;
        return this;
    }

    public void setType(TypePriseEnCharge type) {
        this.type = type;
    }

    public Orthophoniste getOrthophoniste() {
        return orthophoniste;
    }

    public EtatRecapitulatif orthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
        return this;
    }

    public void setOrthophoniste(Orthophoniste orthophoniste) {
        this.orthophoniste = orthophoniste;
    }

    public Set<FeuilleSoins> getFeuilleSoins() {
        return feuilleSoins;
    }

    public EtatRecapitulatif feuilleSoins(Set<FeuilleSoins> feuilleSoins) {
        this.feuilleSoins = feuilleSoins;
        return this;
    }

    public EtatRecapitulatif addFeuilleSoins(FeuilleSoins feuilleSoins) {
        this.feuilleSoins.add(feuilleSoins);
        feuilleSoins.setEtatRecapitulatif(this);
        return this;
    }

    public EtatRecapitulatif removeFeuilleSoins(FeuilleSoins feuilleSoins) {
        this.feuilleSoins.remove(feuilleSoins);
        feuilleSoins.setEtatRecapitulatif(null);
        return this;
    }

    public void setFeuilleSoins(Set<FeuilleSoins> feuilleSoins) {
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
        EtatRecapitulatif etatRecapitulatif = (EtatRecapitulatif) o;
        if (etatRecapitulatif.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etatRecapitulatif.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtatRecapitulatif{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
