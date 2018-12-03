import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    CourrielComponent,
    CourrielDetailComponent,
    CourrielUpdateComponent,
    CourrielDeletePopupComponent,
    CourrielDeleteDialogComponent,
    courrielRoute,
    courrielPopupRoute
} from './';

const ENTITY_STATES = [...courrielRoute, ...courrielPopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CourrielComponent,
        CourrielDetailComponent,
        CourrielUpdateComponent,
        CourrielDeleteDialogComponent,
        CourrielDeletePopupComponent
    ],
    entryComponents: [CourrielComponent, CourrielUpdateComponent, CourrielDeleteDialogComponent, CourrielDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsCourrielModule {}
