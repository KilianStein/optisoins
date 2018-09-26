import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    EtatRecapitulatifComponent,
    EtatRecapitulatifDetailComponent,
    EtatRecapitulatifUpdateComponent,
    EtatRecapitulatifDeletePopupComponent,
    EtatRecapitulatifDeleteDialogComponent,
    etatRecapitulatifRoute,
    etatRecapitulatifPopupRoute
} from './';

const ENTITY_STATES = [...etatRecapitulatifRoute, ...etatRecapitulatifPopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EtatRecapitulatifComponent,
        EtatRecapitulatifDetailComponent,
        EtatRecapitulatifUpdateComponent,
        EtatRecapitulatifDeleteDialogComponent,
        EtatRecapitulatifDeletePopupComponent
    ],
    entryComponents: [
        EtatRecapitulatifComponent,
        EtatRecapitulatifUpdateComponent,
        EtatRecapitulatifDeleteDialogComponent,
        EtatRecapitulatifDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsEtatRecapitulatifModule {}
