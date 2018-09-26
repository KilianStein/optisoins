package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DemandeEntentePrealable.
 */
@Entity
@Table(name = "demande_entente_prealable")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DemandeEntentePrealable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_acp", nullable = false)
    private String numeroACP;

    @NotNull
    @Column(name = "nombre_seances", nullable = false)
    private Integer nombreSeances;

    @ManyToOne
    @JsonIgnoreProperties("demandeEntentePrealables")
    private Ordonnance ordonnance;

    @OneToOne
    @JoinColumn(unique = true)
    private Amo amo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroACP() {
        return numeroACP;
    }

    public DemandeEntentePrealable numeroACP(String numeroACP) {
        this.numeroACP = numeroACP;
        return this;
    }

    public void setNumeroACP(String numeroACP) {
        this.numeroACP = numeroACP;
    }

    public Integer getNombreSeances() {
        return nombreSeances;
    }

    public DemandeEntentePrealable nombreSeances(Integer nombreSeances) {
        this.nombreSeances = nombreSeances;
        return this;
    }

    public void setNombreSeances(Integer nombreSeances) {
        this.nombreSeances = nombreSeances;
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }

    public DemandeEntentePrealable ordonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
        return this;
    }

    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }

    public Amo getAmo() {
        return amo;
    }

    public DemandeEntentePrealable amo(Amo amo) {
        this.amo = amo;
        return this;
    }

    public void setAmo(Amo amo) {
        this.amo = amo;
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
        DemandeEntentePrealable demandeEntentePrealable = (DemandeEntentePrealable) o;
        if (demandeEntentePrealable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), demandeEntentePrealable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DemandeEntentePrealable{" +
            "id=" + getId() +
            ", numeroACP='" + getNumeroACP() + "'" +
            ", nombreSeances=" + getNombreSeances() +
            "}";
    }
}
