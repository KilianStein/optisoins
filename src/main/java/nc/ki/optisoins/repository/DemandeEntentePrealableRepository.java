package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.DemandeEntentePrealable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DemandeEntentePrealable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeEntentePrealableRepository extends JpaRepository<DemandeEntentePrealable, Long> {

}
