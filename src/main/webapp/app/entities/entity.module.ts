import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { OptisoinsOrthophonisteModule } from './orthophoniste/orthophoniste.module';
import { OptisoinsPatienteleModule } from './patientele/patientele.module';
import { OptisoinsRemplacanteModule } from './remplacante/remplacante.module';
import { OptisoinsCompteBancaireModule } from './compte-bancaire/compte-bancaire.module';
import { OptisoinsAdresseModule } from './adresse/adresse.module';
import { OptisoinsTelephoneModule } from './telephone/telephone.module';
import { OptisoinsCourrielModule } from './courriel/courriel.module';
import { OptisoinsPatientModule } from './patient/patient.module';
import { OptisoinsMutuelleModule } from './mutuelle/mutuelle.module';
import { OptisoinsAssureModule } from './assure/assure.module';
import { OptisoinsCarteAideMedicaleModule } from './carte-aide-medicale/carte-aide-medicale.module';
import { OptisoinsMedecinModule } from './medecin/medecin.module';
import { OptisoinsOrdonnanceModule } from './ordonnance/ordonnance.module';
import { OptisoinsAmoModule } from './amo/amo.module';
import { OptisoinsSeanceModule } from './seance/seance.module';
import { OptisoinsPriseEnChargeModule } from './prise-en-charge/prise-en-charge.module';
import { OptisoinsFeuilleSoinsModule } from './feuille-soins/feuille-soins.module';
import { OptisoinsDemandeEntentePrealableModule } from './demande-entente-prealable/demande-entente-prealable.module';
import { OptisoinsEtatRecapitulatifModule } from './etat-recapitulatif/etat-recapitulatif.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        OptisoinsOrthophonisteModule,
        OptisoinsPatienteleModule,
        OptisoinsRemplacanteModule,
        OptisoinsCompteBancaireModule,
        OptisoinsAdresseModule,
        OptisoinsTelephoneModule,
        OptisoinsCourrielModule,
        OptisoinsPatientModule,
        OptisoinsMutuelleModule,
        OptisoinsAssureModule,
        OptisoinsCarteAideMedicaleModule,
        OptisoinsMedecinModule,
        OptisoinsOrdonnanceModule,
        OptisoinsAmoModule,
        OptisoinsSeanceModule,
        OptisoinsPriseEnChargeModule,
        OptisoinsFeuilleSoinsModule,
        OptisoinsDemandeEntentePrealableModule,
        OptisoinsEtatRecapitulatifModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsEntityModule {}
