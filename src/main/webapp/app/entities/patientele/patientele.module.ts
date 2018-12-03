import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OptisoinsSharedModule } from 'app/shared';
import {
    PatienteleComponent,
    PatienteleDetailComponent,
    PatienteleUpdateComponent,
    PatienteleDeletePopupComponent,
    PatienteleDeleteDialogComponent,
    patienteleRoute,
    patientelePopupRoute
} from './';

const ENTITY_STATES = [...patienteleRoute, ...patientelePopupRoute];

@NgModule({
    imports: [OptisoinsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PatienteleComponent,
        PatienteleDetailComponent,
        PatienteleUpdateComponent,
        PatienteleDeleteDialogComponent,
        PatienteleDeletePopupComponent
    ],
    entryComponents: [PatienteleComponent, PatienteleUpdateComponent, PatienteleDeleteDialogComponent, PatienteleDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class OptisoinsPatienteleModule {}
