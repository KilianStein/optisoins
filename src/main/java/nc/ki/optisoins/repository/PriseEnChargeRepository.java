package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.PriseEnCharge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PriseEnCharge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriseEnChargeRepository extends JpaRepository<PriseEnCharge, Long> {

}
