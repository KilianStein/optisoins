package nc.ki.optisoins.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import nc.ki.optisoins.domain.enumeration.TypeCourriel;

/**
 * A DTO for the Courriel entity.
 */
public class CourrielDTO implements Serializable {

    private Long id;

    @NotNull
    private String email;

    @NotNull
    private TypeCourriel type;

    private String commentaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TypeCourriel getType() {
        return type;
    }

    public void setType(TypeCourriel type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourrielDTO courrielDTO = (CourrielDTO) o;
        if (courrielDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courrielDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourrielDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", type='" + getType() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
