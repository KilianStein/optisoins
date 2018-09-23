import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    OrthophonisteComponent,
    OrthophonisteDetailComponent,
    OrthophonisteUpdateComponent,
    OrthophonisteDeletePopupComponent,
    OrthophonisteDeleteDialogComponent,
    orthophonisteRoute,
    orthophonistePopupRoute
} from './';

const ENTITY_STATES = [...orthophonisteRoute, ...orthophonistePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrthophonisteComponent,
        OrthophonisteDetailComponent,
        OrthophonisteUpdateComponent,
        OrthophonisteDeleteDialogComponent,
        OrthophonisteDeletePopupComponent
    ],
    entryComponents: [
        OrthophonisteComponent,
        OrthophonisteUpdateComponent,
        OrthophonisteDeleteDialogComponent,
        OrthophonisteDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsOrthophonisteModule {}
