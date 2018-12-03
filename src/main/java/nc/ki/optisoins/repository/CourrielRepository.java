package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.Courriel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Courriel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourrielRepository extends JpaRepository<Courriel, Long> {

}
