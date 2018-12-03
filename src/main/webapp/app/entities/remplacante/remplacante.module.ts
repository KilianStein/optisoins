import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    RemplacanteComponent,
    RemplacanteDetailComponent,
    RemplacanteUpdateComponent,
    RemplacanteDeletePopupComponent,
    RemplacanteDeleteDialogComponent,
    remplacanteRoute,
    remplacantePopupRoute
} from './';

const ENTITY_STATES = [...remplacanteRoute, ...remplacantePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RemplacanteComponent,
        RemplacanteDetailComponent,
        RemplacanteUpdateComponent,
        RemplacanteDeleteDialogComponent,
        RemplacanteDeletePopupComponent
    ],
    entryComponents: [RemplacanteComponent, RemplacanteUpdateComponent, RemplacanteDeleteDialogComponent, RemplacanteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsRemplacanteModule {}
