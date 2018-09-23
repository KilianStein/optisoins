import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    CompteBancaireComponent,
    CompteBancaireDetailComponent,
    CompteBancaireUpdateComponent,
    CompteBancaireDeletePopupComponent,
    CompteBancaireDeleteDialogComponent,
    compteBancaireRoute,
    compteBancairePopupRoute
} from './';

const ENTITY_STATES = [...compteBancaireRoute, ...compteBancairePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompteBancaireComponent,
        CompteBancaireDetailComponent,
        CompteBancaireUpdateComponent,
        CompteBancaireDeleteDialogComponent,
        CompteBancaireDeletePopupComponent
    ],
    entryComponents: [
        CompteBancaireComponent,
        CompteBancaireUpdateComponent,
        CompteBancaireDeleteDialogComponent,
        CompteBancaireDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsCompteBancaireModule {}
