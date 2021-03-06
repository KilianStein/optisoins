package nc.ki.optisoins.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Medecin.
 */
@Entity
@Table(name = "medecin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medecin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "identifiant")
    private String identifiant;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "medecin_adresses",
               joinColumns = @JoinColumn(name = "medecins_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "adresses_id", referencedColumnName = "id"))
    private Set<Adresse> adresses = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "medecin_courriels",
               joinColumns = @JoinColumn(name = "medecins_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "courriels_id", referencedColumnName = "id"))
    private Set<Courriel> courriels = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "medecin_telephones",
               joinColumns = @JoinColumn(name = "medecins_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "telephones_id", referencedColumnName = "id"))
    private Set<Telephone> telephones = new HashSet<>();

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

    public Medecin nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public Medecin identifiant(String identifiant) {
        this.identifiant = identifiant;
        return this;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public Set<Adresse> getAdresses() {
        return adresses;
    }

    public Medecin adresses(Set<Adresse> adresses) {
        this.adresses = adresses;
        return this;
    }

    public Medecin addAdresses(Adresse adresse) {
        this.adresses.add(adresse);
        return this;
    }

    public Medecin removeAdresses(Adresse adresse) {
        this.adresses.remove(adresse);
        return this;
    }

    public void setAdresses(Set<Adresse> adresses) {
        this.adresses = adresses;
    }

    public Set<Courriel> getCourriels() {
        return courriels;
    }

    public Medecin courriels(Set<Courriel> courriels) {
        this.courriels = courriels;
        return this;
    }

    public Medecin addCourriels(Courriel courriel) {
        this.courriels.add(courriel);
        return this;
    }

    public Medecin removeCourriels(Courriel courriel) {
        this.courriels.remove(courriel);
        return this;
    }

    public void setCourriels(Set<Courriel> courriels) {
        this.courriels = courriels;
    }

    public Set<Telephone> getTelephones() {
        return telephones;
    }

    public Medecin telephones(Set<Telephone> telephones) {
        this.telephones = telephones;
        return this;
    }

    public Medecin addTelephones(Telephone telephone) {
        this.telephones.add(telephone);
        return this;
    }

    public Medecin removeTelephones(Telephone telephone) {
        this.telephones.remove(telephone);
        return this;
    }

    public void setTelephones(Set<Telephone> telephones) {
        this.telephones = telephones;
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
        Medecin medecin = (Medecin) o;
        if (medecin.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medecin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medecin{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", identifiant='" + getIdentifiant() + "'" +
            "}";
    }
}
