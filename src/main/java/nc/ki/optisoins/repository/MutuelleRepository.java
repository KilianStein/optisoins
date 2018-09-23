package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.Mutuelle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Mutuelle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MutuelleRepository extends JpaRepository<Mutuelle, Long> {

}
