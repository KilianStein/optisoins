import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    AssureComponent,
    AssureDetailComponent,
    AssureUpdateComponent,
    AssureDeletePopupComponent,
    AssureDeleteDialogComponent,
    assureRoute,
    assurePopupRoute
} from './';

const ENTITY_STATES = [...assureRoute, ...assurePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AssureComponent, AssureDetailComponent, AssureUpdateComponent, AssureDeleteDialogComponent, AssureDeletePopupComponent],
    entryComponents: [AssureComponent, AssureUpdateComponent, AssureDeleteDialogComponent, AssureDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsAssureModule {}
