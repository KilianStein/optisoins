import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    AmoComponent,
    AmoDetailComponent,
    AmoUpdateComponent,
    AmoDeletePopupComponent,
    AmoDeleteDialogComponent,
    amoRoute,
    amoPopupRoute
} from './';

const ENTITY_STATES = [...amoRoute, ...amoPopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AmoComponent, AmoDetailComponent, AmoUpdateComponent, AmoDeleteDialogComponent, AmoDeletePopupComponent],
    entryComponents: [AmoComponent, AmoUpdateComponent, AmoDeleteDialogComponent, AmoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsAmoModule {}
