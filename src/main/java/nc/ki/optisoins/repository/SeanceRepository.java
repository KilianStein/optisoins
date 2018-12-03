package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.Seance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Seance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

}
