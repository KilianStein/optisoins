import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    PriseEnChargeComponent,
    PriseEnChargeDetailComponent,
    PriseEnChargeUpdateComponent,
    PriseEnChargeDeletePopupComponent,
    PriseEnChargeDeleteDialogComponent,
    priseEnChargeRoute,
    priseEnChargePopupRoute
} from './';

const ENTITY_STATES = [...priseEnChargeRoute, ...priseEnChargePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PriseEnChargeComponent,
        PriseEnChargeDetailComponent,
        PriseEnChargeUpdateComponent,
        PriseEnChargeDeleteDialogComponent,
        PriseEnChargeDeletePopupComponent
    ],
    entryComponents: [
        PriseEnChargeComponent,
        PriseEnChargeUpdateComponent,
        PriseEnChargeDeleteDialogComponent,
        PriseEnChargeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsPriseEnChargeModule {}
