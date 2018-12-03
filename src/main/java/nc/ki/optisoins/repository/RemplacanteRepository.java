package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.Remplacante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Remplacante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemplacanteRepository extends JpaRepository<Remplacante, Long> {

}
