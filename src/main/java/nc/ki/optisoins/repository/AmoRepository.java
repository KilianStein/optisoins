package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.Amo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Amo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmoRepository extends JpaRepository<Amo, Long> {

}
