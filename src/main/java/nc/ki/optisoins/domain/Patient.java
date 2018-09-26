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

import nc.ki.optisoins.domain.enumeration.SituationPatient;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "numero_cafat")
    private String numeroCafat;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "employeur")
    private String employeur;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "situation", nullable = false)
    private SituationPatient situation;

    @ManyToOne
    @JsonIgnoreProperties("patients")
    private Patientele patientele;

    @OneToOne
    @JoinColumn(unique = true)
    private Assure assure;

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PriseEnCharge> prisesEnCharges = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Telephone> telephones = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Adresse> adresses = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Courriel> courriels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public Patient prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Patient nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumeroCafat() {
        return numeroCafat;
    }

    public Patient numeroCafat(String numeroCafat) {
        this.numeroCafat = numeroCafat;
        return this;
    }

    public void setNumeroCafat(String numeroCafat) {
        this.numeroCafat = numeroCafat;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Patient dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmployeur() {
        return employeur;
    }

    public Patient employeur(String employeur) {
        this.employeur = employeur;
        return this;
    }

    public void setEmployeur(String employeur) {
        this.employeur = employeur;
    }

    public SituationPatient getSituation() {
        return situation;
    }

    public Patient situation(SituationPatient situation) {
        this.situation = situation;
        return this;
    }

    public void setSituation(SituationPatient situation) {
        this.situation = situation;
    }

    public Patientele getPatientele() {
        return patientele;
    }

    public Patient patientele(Patientele patientele) {
        this.patientele = patientele;
        return this;
    }

    public void setPatientele(Patientele patientele) {
        this.patientele = patientele;
    }

    public Assure getAssure() {
        return assure;
    }

    public Patient assure(Assure assure) {
        this.assure = assure;
        return this;
    }

    public void setAssure(Assure assure) {
        this.assure = assure;
    }

    public Set<PriseEnCharge> getPrisesEnCharges() {
        return prisesEnCharges;
    }

    public Patient prisesEnCharges(Set<PriseEnCharge> priseEnCharges) {
        this.prisesEnCharges = priseEnCharges;
        return this;
    }

    public Patient addPrisesEnCharge(PriseEnCharge priseEnCharge) {
        this.prisesEnCharges.add(priseEnCharge);
        priseEnCharge.setPatient(this);
        return this;
    }

    public Patient removePrisesEnCharge(PriseEnCharge priseEnCharge) {
        this.prisesEnCharges.remove(priseEnCharge);
        priseEnCharge.setPatient(null);
        return this;
    }

    public void setPrisesEnCharges(Set<PriseEnCharge> priseEnCharges) {
        this.prisesEnCharges = priseEnCharges;
    }

    public Set<Telephone> getTelephones() {
        return telephones;
    }

    public Patient telephones(Set<Telephone> telephones) {
        this.telephones = telephones;
        return this;
    }

    public Patient addTelephones(Telephone telephone) {
        this.telephones.add(telephone);
        telephone.setPatient(this);
        return this;
    }

    public Patient removeTelephones(Telephone telephone) {
        this.telephones.remove(telephone);
        telephone.setPatient(null);
        return this;
    }

    public void setTelephones(Set<Telephone> telephones) {
        this.telephones = telephones;
    }

    public Set<Adresse> getAdresses() {
        return adresses;
    }

    public Patient adresses(Set<Adresse> adresses) {
        this.adresses = adresses;
        return this;
    }

    public Patient addAdresses(Adresse adresse) {
        this.adresses.add(adresse);
        adresse.setPatient(this);
        return this;
    }

    public Patient removeAdresses(Adresse adresse) {
        this.adresses.remove(adresse);
        adresse.setPatient(null);
        return this;
    }

    public void setAdresses(Set<Adresse> adresses) {
        this.adresses = adresses;
    }

    public Set<Courriel> getCourriels() {
        return courriels;
    }

    public Patient courriels(Set<Courriel> courriels) {
        this.courriels = courriels;
        return this;
    }

    public Patient addCourriels(Courriel courriel) {
        this.courriels.add(courriel);
        courriel.setPatient(this);
        return this;
    }

    public Patient removeCourriels(Courriel courriel) {
        this.courriels.remove(courriel);
        courriel.setPatient(null);
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
        Patient patient = (Patient) o;
        if (patient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", numeroCafat='" + getNumeroCafat() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", employeur='" + getEmployeur() + "'" +
            ", situation='" + getSituation() + "'" +
            "}";
    }
}
