package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.Orthophoniste;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Orthophoniste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrthophonisteRepository extends JpaRepository<Orthophoniste, Long> {

}
