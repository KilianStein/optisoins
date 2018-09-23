package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.CompteBancaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompteBancaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Long> {

}
