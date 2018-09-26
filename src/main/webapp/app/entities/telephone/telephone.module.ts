import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    TelephoneComponent,
    TelephoneDetailComponent,
    TelephoneUpdateComponent,
    TelephoneDeletePopupComponent,
    TelephoneDeleteDialogComponent,
    telephoneRoute,
    telephonePopupRoute
} from './';

const ENTITY_STATES = [...telephoneRoute, ...telephonePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TelephoneComponent,
        TelephoneDetailComponent,
        TelephoneUpdateComponent,
        TelephoneDeleteDialogComponent,
        TelephoneDeletePopupComponent
    ],
    entryComponents: [TelephoneComponent, TelephoneUpdateComponent, TelephoneDeleteDialogComponent, TelephoneDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsTelephoneModule {}
