package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.FeuilleSoins;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FeuilleSoins entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeuilleSoinsRepository extends JpaRepository<FeuilleSoins, Long> {

}
