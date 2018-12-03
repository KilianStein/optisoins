package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.CarteAideMedicale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CarteAideMedicale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarteAideMedicaleRepository extends JpaRepository<CarteAideMedicale, Long> {

}
