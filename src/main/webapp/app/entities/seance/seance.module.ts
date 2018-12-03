import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    SeanceComponent,
    SeanceDetailComponent,
    SeanceUpdateComponent,
    SeanceDeletePopupComponent,
    SeanceDeleteDialogComponent,
    seanceRoute,
    seancePopupRoute
} from './';

const ENTITY_STATES = [...seanceRoute, ...seancePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SeanceComponent, SeanceDetailComponent, SeanceUpdateComponent, SeanceDeleteDialogComponent, SeanceDeletePopupComponent],
    entryComponents: [SeanceComponent, SeanceUpdateComponent, SeanceDeleteDialogComponent, SeanceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsSeanceModule {}
