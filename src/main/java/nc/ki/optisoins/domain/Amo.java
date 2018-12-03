package nc.ki.optisoins.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Amo.
 */
@Entity
@Table(name = "amo")
public class Amo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "tarif")
    private Integer tarif;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Amo code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTarif() {
        return tarif;
    }

    public Amo tarif(Integer tarif) {
        this.tarif = tarif;
        return this;
    }

    public void setTarif(Integer tarif) {
        this.tarif = tarif;
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
        Amo amo = (Amo) o;
        if (amo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Amo{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", tarif=" + getTarif() +
            "}";
    }
}
