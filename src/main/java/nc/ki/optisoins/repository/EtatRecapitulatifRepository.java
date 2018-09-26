package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.EtatRecapitulatif;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EtatRecapitulatif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatRecapitulatifRepository extends JpaRepository<EtatRecapitulatif, Long> {

}
