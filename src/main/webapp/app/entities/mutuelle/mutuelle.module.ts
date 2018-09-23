import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    MutuelleComponent,
    MutuelleDetailComponent,
    MutuelleUpdateComponent,
    MutuelleDeletePopupComponent,
    MutuelleDeleteDialogComponent,
    mutuelleRoute,
    mutuellePopupRoute
} from './';

const ENTITY_STATES = [...mutuelleRoute, ...mutuellePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MutuelleComponent,
        MutuelleDetailComponent,
        MutuelleUpdateComponent,
        MutuelleDeleteDialogComponent,
        MutuelleDeletePopupComponent
    ],
    entryComponents: [MutuelleComponent, MutuelleUpdateComponent, MutuelleDeleteDialogComponent, MutuelleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsMutuelleModule {}
