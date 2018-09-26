package nc.ki.optisoins.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(nc.ki.optisoins.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Orthophoniste.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Orthophoniste.class.getName() + ".patienteles", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Orthophoniste.class.getName() + ".etatsRecapitulatifs", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Orthophoniste.class.getName() + ".telephones", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Orthophoniste.class.getName() + ".adresses", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Orthophoniste.class.getName() + ".courriels", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Patientele.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Patientele.class.getName() + ".patients", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Patientele.class.getName() + ".remplacantes", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Remplacante.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.CompteBancaire.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Adresse.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Telephone.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Courriel.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Patient.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Patient.class.getName() + ".prisesEnCharges", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Patient.class.getName() + ".telephones", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Patient.class.getName() + ".adresses", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Patient.class.getName() + ".courriels", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Mutuelle.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Assure.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Assure.class.getName() + ".cartesAideMedicales", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Assure.class.getName() + ".mutuelles", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.CarteAideMedicale.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Medecin.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Medecin.class.getName() + ".adresses", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Medecin.class.getName() + ".courriels", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Medecin.class.getName() + ".telephones", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Ordonnance.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Ordonnance.class.getName() + ".demandeEntentePrealables", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Amo.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.Seance.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.PriseEnCharge.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.PriseEnCharge.class.getName() + ".ordonnances", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.PriseEnCharge.class.getName() + ".seances", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.PriseEnCharge.class.getName() + ".feuillesSoins", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.FeuilleSoins.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.FeuilleSoins.class.getName() + ".actes", jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.DemandeEntentePrealable.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.EtatRecapitulatif.class.getName(), jcacheConfiguration);
            cm.createCache(nc.ki.optisoins.domain.EtatRecapitulatif.class.getName() + ".feuilleSoins", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
