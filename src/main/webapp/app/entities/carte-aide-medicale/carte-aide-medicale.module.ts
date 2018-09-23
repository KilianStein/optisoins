import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    CarteAideMedicaleComponent,
    CarteAideMedicaleDetailComponent,
    CarteAideMedicaleUpdateComponent,
    CarteAideMedicaleDeletePopupComponent,
    CarteAideMedicaleDeleteDialogComponent,
    carteAideMedicaleRoute,
    carteAideMedicalePopupRoute
} from './';

const ENTITY_STATES = [...carteAideMedicaleRoute, ...carteAideMedicalePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CarteAideMedicaleComponent,
        CarteAideMedicaleDetailComponent,
        CarteAideMedicaleUpdateComponent,
        CarteAideMedicaleDeleteDialogComponent,
        CarteAideMedicaleDeletePopupComponent
    ],
    entryComponents: [
        CarteAideMedicaleComponent,
        CarteAideMedicaleUpdateComponent,
        CarteAideMedicaleDeleteDialogComponent,
        CarteAideMedicaleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsCarteAideMedicaleModule {}
