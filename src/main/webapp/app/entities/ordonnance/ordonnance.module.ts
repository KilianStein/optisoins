import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    OrdonnanceComponent,
    OrdonnanceDetailComponent,
    OrdonnanceUpdateComponent,
    OrdonnanceDeletePopupComponent,
    OrdonnanceDeleteDialogComponent,
    ordonnanceRoute,
    ordonnancePopupRoute
} from './';

const ENTITY_STATES = [...ordonnanceRoute, ...ordonnancePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrdonnanceComponent,
        OrdonnanceDetailComponent,
        OrdonnanceUpdateComponent,
        OrdonnanceDeleteDialogComponent,
        OrdonnanceDeletePopupComponent
    ],
    entryComponents: [OrdonnanceComponent, OrdonnanceUpdateComponent, OrdonnanceDeleteDialogComponent, OrdonnanceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsOrdonnanceModule {}
