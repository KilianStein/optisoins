package nc.ki.optisoins.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Amo entity.
 */
public class AmoDTO implements Serializable {

    private Long id;

    private String code;

    private Integer tarif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTarif() {
        return tarif;
    }

    public void setTarif(Integer tarif) {
        this.tarif = tarif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmoDTO amoDTO = (AmoDTO) o;
        if (amoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmoDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", tarif=" + getTarif() +
            "}";
    }
}
