import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    FeuilleSoinsComponent,
    FeuilleSoinsDetailComponent,
    FeuilleSoinsUpdateComponent,
    FeuilleSoinsDeletePopupComponent,
    FeuilleSoinsDeleteDialogComponent,
    feuilleSoinsRoute,
    feuilleSoinsPopupRoute
} from './';

const ENTITY_STATES = [...feuilleSoinsRoute, ...feuilleSoinsPopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FeuilleSoinsComponent,
        FeuilleSoinsDetailComponent,
        FeuilleSoinsUpdateComponent,
        FeuilleSoinsDeleteDialogComponent,
        FeuilleSoinsDeletePopupComponent
    ],
    entryComponents: [
        FeuilleSoinsComponent,
        FeuilleSoinsUpdateComponent,
        FeuilleSoinsDeleteDialogComponent,
        FeuilleSoinsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsFeuilleSoinsModule {}
