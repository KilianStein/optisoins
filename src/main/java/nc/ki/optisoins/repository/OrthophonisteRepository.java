package nc.ki.optisoins.repository;

import nc.ki.optisoins.domain.Orthophoniste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Orthophoniste entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrthophonisteRepository extends JpaRepository<Orthophoniste, Long> {

    @Query(value = "select distinct orthophoniste from Orthophoniste orthophoniste left join fetch orthophoniste.telephones left join fetch orthophoniste.adresses left join fetch orthophoniste.courriels",
        countQuery = "select count(distinct orthophoniste) from Orthophoniste orthophoniste")
    Page<Orthophoniste> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct orthophoniste from Orthophoniste orthophoniste left join fetch orthophoniste.telephones left join fetch orthophoniste.adresses left join fetch orthophoniste.courriels")
    List<Orthophoniste> findAllWithEagerRelationships();

    @Query("select orthophoniste from Orthophoniste orthophoniste left join fetch orthophoniste.telephones left join fetch orthophoniste.adresses left join fetch orthophoniste.courriels where orthophoniste.id =:id")
    Optional<Orthophoniste> findOneWithEagerRelationships(@Param("id") Long id);

}
