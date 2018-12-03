package nc.ki.optisoins.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Orthophoniste.
 */
@Entity
@Table(name = "orthophoniste")
public class Orthophoniste implements Serializable {

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

    @Column(name = "numero_cafat")
    private String numeroCafat;

    @Column(name = "numero_ridet")
    private String numeroRidet;

    @OneToOne    @JoinColumn(unique = true)
    private CompteBancaire compteBancaire;

    @OneToMany(mappedBy = "orthophoniste")
    private Set<Patientele> patienteles = new HashSet<>();
    @OneToMany(mappedBy = "orthophoniste")
    private Set<EtatRecapitulatif> etatsRecapitulatifs = new HashSet<>();
    @OneToMany(mappedBy = "orthophoniste")
    private Set<Telephone> telephones = new HashSet<>();
    @OneToMany(mappedBy = "orthophoniste")
    private Set<Adresse> adresses = new HashSet<>();
    @OneToMany(mappedBy = "orthophoniste")
    private Set<Courriel> courriels = new HashSet<>();
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

    public Orthophoniste nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Orthophoniste prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroCafat() {
        return numeroCafat;
    }

    public Orthophoniste numeroCafat(String numeroCafat) {
        this.numeroCafat = numeroCafat;
        return this;
    }

    public void setNumeroCafat(String numeroCafat) {
        this.numeroCafat = numeroCafat;
    }

    public String getNumeroRidet() {
        return numeroRidet;
    }

    public Orthophoniste numeroRidet(String numeroRidet) {
        this.numeroRidet = numeroRidet;
        return this;
    }

    public void setNumeroRidet(String numeroRidet) {
        this.numeroRidet = numeroRidet;
    }

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public Orthophoniste compteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
        return this;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }

    public Set<Patientele> getPatienteles() {
        return patienteles;
    }

    public Orthophoniste patienteles(Set<Patientele> patienteles) {
        this.patienteles = patienteles;
        return this;
    }

    public Orthophoniste addPatientele(Patientele patientele) {
        this.patienteles.add(patientele);
        patientele.setOrthophoniste(this);
        return this;
    }

    public Orthophoniste removePatientele(Patientele patientele) {
        this.patienteles.remove(patientele);
        patientele.setOrthophoniste(null);
        return this;
    }

    public void setPatienteles(Set<Patientele> patienteles) {
        this.patienteles = patienteles;
    }

    public Set<EtatRecapitulatif> getEtatsRecapitulatifs() {
        return etatsRecapitulatifs;
    }

    public Orthophoniste etatsRecapitulatifs(Set<EtatRecapitulatif> etatRecapitulatifs) {
        this.etatsRecapitulatifs = etatRecapitulatifs;
        return this;
    }

    public Orthophoniste addEtatsRecapitulatif(EtatRecapitulatif etatRecapitulatif) {
        this.etatsRecapitulatifs.add(etatRecapitulatif);
        etatRecapitulatif.setOrthophoniste(this);
        return this;
    }

    public Orthophoniste removeEtatsRecapitulatif(EtatRecapitulatif etatRecapitulatif) {
        this.etatsRecapitulatifs.remove(etatRecapitulatif);
        etatRecapitulatif.setOrthophoniste(null);
        return this;
    }

    public void setEtatsRecapitulatifs(Set<EtatRecapitulatif> etatRecapitulatifs) {
        this.etatsRecapitulatifs = etatRecapitulatifs;
    }

    public Set<Telephone> getTelephones() {
        return telephones;
    }

    public Orthophoniste telephones(Set<Telephone> telephones) {
        this.telephones = telephones;
        return this;
    }

    public Orthophoniste addTelephones(Telephone telephone) {
        this.telephones.add(telephone);
        telephone.setOrthophoniste(this);
        return this;
    }

    public Orthophoniste removeTelephones(Telephone telephone) {
        this.telephones.remove(telephone);
        telephone.setOrthophoniste(null);
        return this;
    }

    public void setTelephones(Set<Telephone> telephones) {
        this.telephones = telephones;
    }

    public Set<Adresse> getAdresses() {
        return adresses;
    }

    public Orthophoniste adresses(Set<Adresse> adresses) {
        this.adresses = adresses;
        return this;
    }

    public Orthophoniste addAdresses(Adresse adresse) {
        this.adresses.add(adresse);
        adresse.setOrthophoniste(this);
        return this;
    }

    public Orthophoniste removeAdresses(Adresse adresse) {
        this.adresses.remove(adresse);
        adresse.setOrthophoniste(null);
        return this;
    }

    public void setAdresses(Set<Adresse> adresses) {
        this.adresses = adresses;
    }

    public Set<Courriel> getCourriels() {
        return courriels;
    }

    public Orthophoniste courriels(Set<Courriel> courriels) {
        this.courriels = courriels;
        return this;
    }

    public Orthophoniste addCourriels(Courriel courriel) {
        this.courriels.add(courriel);
        courriel.setOrthophoniste(this);
        return this;
    }

    public Orthophoniste removeCourriels(Courriel courriel) {
        this.courriels.remove(courriel);
        courriel.setOrthophoniste(null);
        return this;
    }

    public void setCourriels(Set<Courriel> courriels) {
        this.courriels = courriels;
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
        Orthophoniste orthophoniste = (Orthophoniste) o;
        if (orthophoniste.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orthophoniste.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Orthophoniste{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", numeroCafat='" + getNumeroCafat() + "'" +
            ", numeroRidet='" + getNumeroRidet() + "'" +
            "}";
    }
}
