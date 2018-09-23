package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import nc.ki.optisoins.domain.enumeration.TypeLienAssure;

/**
 * A Assure.
 */
@Entity
@Table(name = "assure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Assure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "numero_cafat", nullable = false)
    private String numeroCafat;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "lien_assure")
    private TypeLienAssure lienAssure;

    @OneToOne
    @JoinColumn(unique = true)
    private Courriel courriel;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adresse;

    @OneToOne
    @JoinColumn(unique = true)
    private Telephone telephone;

    @OneToMany(mappedBy = "assure")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CarteAideMedicale> cartesAideMedicales = new HashSet<>();

    @OneToMany(mappedBy = "assure")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mutuelle> mutuelles = new HashSet<>();

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

    public Assure nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Assure prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroCafat() {
        return numeroCafat;
    }

    public Assure numeroCafat(String numeroCafat) {
        this.numeroCafat = numeroCafat;
        return this;
    }

    public void setNumeroCafat(String numeroCafat) {
        this.numeroCafat = numeroCafat;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Assure dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public TypeLienAssure getLienAssure() {
        return lienAssure;
    }

    public Assure lienAssure(TypeLienAssure lienAssure) {
        this.lienAssure = lienAssure;
        return this;
    }

    public void setLienAssure(TypeLienAssure lienAssure) {
        this.lienAssure = lienAssure;
    }

    public Courriel getCourriel() {
        return courriel;
    }

    public Assure courriel(Courriel courriel) {
        this.courriel = courriel;
        return this;
    }

    public void setCourriel(Courriel courriel) {
        this.courriel = courriel;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public Assure adresse(Adresse adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public Assure telephone(Telephone telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }

    public Set<CarteAideMedicale> getCartesAideMedicales() {
        return cartesAideMedicales;
    }

    public Assure cartesAideMedicales(Set<CarteAideMedicale> carteAideMedicales) {
        this.cartesAideMedicales = carteAideMedicales;
        return this;
    }

    public Assure addCartesAideMedicale(CarteAideMedicale carteAideMedicale) {
        this.cartesAideMedicales.add(carteAideMedicale);
        carteAideMedicale.setAssure(this);
        return this;
    }

    public Assure removeCartesAideMedicale(CarteAideMedicale carteAideMedicale) {
        this.cartesAideMedicales.remove(carteAideMedicale);
        carteAideMedicale.setAssure(null);
        return this;
    }

    public void setCartesAideMedicales(Set<CarteAideMedicale> carteAideMedicales) {
        this.cartesAideMedicales = carteAideMedicales;
    }

    public Set<Mutuelle> getMutuelles() {
        return mutuelles;
    }

    public Assure mutuelles(Set<Mutuelle> mutuelles) {
        this.mutuelles = mutuelles;
        return this;
    }

    public Assure addMutuelles(Mutuelle mutuelle) {
        this.mutuelles.add(mutuelle);
        mutuelle.setAssure(this);
        return this;
    }

    public Assure removeMutuelles(Mutuelle mutuelle) {
        this.mutuelles.remove(mutuelle);
        mutuelle.setAssure(null);
        return this;
    }

    public void setMutuelles(Set<Mutuelle> mutuelles) {
        this.mutuelles = mutuelles;
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
        Assure assure = (Assure) o;
        if (assure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Assure{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", numeroCafat='" + getNumeroCafat() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lienAssure='" + getLienAssure() + "'" +
            "}";
    }
}
