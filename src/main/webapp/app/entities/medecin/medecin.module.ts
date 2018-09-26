import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    MedecinComponent,
    MedecinDetailComponent,
    MedecinUpdateComponent,
    MedecinDeletePopupComponent,
    MedecinDeleteDialogComponent,
    medecinRoute,
    medecinPopupRoute
} from './';

const ENTITY_STATES = [...medecinRoute, ...medecinPopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MedecinComponent,
        MedecinDetailComponent,
        MedecinUpdateComponent,
        MedecinDeleteDialogComponent,
        MedecinDeletePopupComponent
    ],
    entryComponents: [MedecinComponent, MedecinUpdateComponent, MedecinDeleteDialogComponent, MedecinDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsMedecinModule {}
