package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.Patientele;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Patientele entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatienteleRepository extends JpaRepository<Patientele, Long> {

}
