package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Medecin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {

    @Query(value = "select distinct medecin from Medecin medecin left join fetch medecin.adresses left join fetch medecin.courriels left join fetch medecin.telephones",
        countQuery = "select count(distinct medecin) from Medecin medecin")
    Page<Medecin> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct medecin from Medecin medecin left join fetch medecin.adresses left join fetch medecin.courriels left join fetch medecin.telephones")
    List<Medecin> findAllWithEagerRelationships();

    @Query("select medecin from Medecin medecin left join fetch medecin.adresses left join fetch medecin.courriels left join fetch medecin.telephones where medecin.id =:id")
    Optional<Medecin> findOneWithEagerRelationships(@Param("id") Long id);

}
