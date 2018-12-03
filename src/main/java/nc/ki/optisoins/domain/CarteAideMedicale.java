package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CarteAideMedicale.
 */
@Entity
@Table(name = "carte_aide_medicale")
public class CarteAideMedicale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_debut_validite", nullable = false)
    private LocalDate dateDebutValidite;

    @NotNull
    @Column(name = "date_fin_validite", nullable = false)
    private LocalDate dateFinValidite;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @ManyToOne
    @JsonIgnoreProperties("cartesAideMedicales")
    private Assure assure;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebutValidite() {
        return dateDebutValidite;
    }

    public CarteAideMedicale dateDebutValidite(LocalDate dateDebutValidite) {
        this.dateDebutValidite = dateDebutValidite;
        return this;
    }

    public void setDateDebutValidite(LocalDate dateDebutValidite) {
        this.dateDebutValidite = dateDebutValidite;
    }

    public LocalDate getDateFinValidite() {
        return dateFinValidite;
    }

    public CarteAideMedicale dateFinValidite(LocalDate dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
        return this;
    }

    public void setDateFinValidite(LocalDate dateFinValidite) {
        this.dateFinValidite = dateFinValidite;
    }

    public String getNumero() {
        return numero;
    }

    public CarteAideMedicale numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Assure getAssure() {
        return assure;
    }

    public CarteAideMedicale assure(Assure assure) {
        this.assure = assure;
        return this;
    }

    public void setAssure(Assure assure) {
        this.assure = assure;
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
        CarteAideMedicale carteAideMedicale = (CarteAideMedicale) o;
        if (carteAideMedicale.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), carteAideMedicale.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CarteAideMedicale{" +
            "id=" + getId() +
            ", dateDebutValidite='" + getDateDebutValidite() + "'" +
            ", dateFinValidite='" + getDateFinValidite() + "'" +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
