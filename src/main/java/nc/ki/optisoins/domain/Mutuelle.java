package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import nc.ki.optisoins.domain.enumeration.TypeMutuelle;

/**
 * A Mutuelle.
 */
@Entity
@Table(name = "mutuelle")
public class Mutuelle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TypeMutuelle type;

    @ManyToOne
    @JsonIgnoreProperties("mutuelles")
    private Assure assure;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Mutuelle nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public Mutuelle numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TypeMutuelle getType() {
        return type;
    }

    public Mutuelle type(TypeMutuelle type) {
        this.type = type;
        return this;
    }

    public void setType(TypeMutuelle type) {
        this.type = type;
    }

    public Assure getAssure() {
        return assure;
    }

    public Mutuelle assure(Assure assure) {
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
        Mutuelle mutuelle = (Mutuelle) o;
        if (mutuelle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mutuelle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mutuelle{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", numero='" + getNumero() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
