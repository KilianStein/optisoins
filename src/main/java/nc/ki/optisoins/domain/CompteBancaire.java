package nc.ki.optisoins.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CompteBancaire.
 */
@Entity
@Table(name = "compte_bancaire")
public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_compte", nullable = false)
    private String numeroCompte;

    @NotNull
    @Column(name = "nom_banque", nullable = false)
    private String nomBanque;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public CompteBancaire numeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
        return this;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getNomBanque() {
        return nomBanque;
    }

    public CompteBancaire nomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
        return this;
    }

    public void setNomBanque(String nomBanque) {
        this.nomBanque = nomBanque;
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
        CompteBancaire compteBancaire = (CompteBancaire) o;
        if (compteBancaire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), compteBancaire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompteBancaire{" +
            "id=" + getId() +
            ", numeroCompte='" + getNumeroCompte() + "'" +
            ", nomBanque='" + getNomBanque() + "'" +
            "}";
    }
}
