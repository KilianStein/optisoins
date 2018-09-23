import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    DemandeEntentePrealableComponent,
    DemandeEntentePrealableDetailComponent,
    DemandeEntentePrealableUpdateComponent,
    DemandeEntentePrealableDeletePopupComponent,
    DemandeEntentePrealableDeleteDialogComponent,
    demandeEntentePrealableRoute,
    demandeEntentePrealablePopupRoute
} from './';

const ENTITY_STATES = [...demandeEntentePrealableRoute, ...demandeEntentePrealablePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DemandeEntentePrealableComponent,
        DemandeEntentePrealableDetailComponent,
        DemandeEntentePrealableUpdateComponent,
        DemandeEntentePrealableDeleteDialogComponent,
        DemandeEntentePrealableDeletePopupComponent
    ],
    entryComponents: [
        DemandeEntentePrealableComponent,
        DemandeEntentePrealableUpdateComponent,
        DemandeEntentePrealableDeleteDialogComponent,
        DemandeEntentePrealableDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsDemandeEntentePrealableModule {}
