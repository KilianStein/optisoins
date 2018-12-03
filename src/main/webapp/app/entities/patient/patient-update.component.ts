import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';
import { IPatientele } from 'app/shared/model/patientele.model';
import { PatienteleService } from 'app/entities/patientele';
import { IAssure } from 'app/shared/model/assure.model';
import { AssureService } from 'app/entities/assure';

@Component({
    selector: 'jhi-patient-update',
    templateUrl: './patient-update.component.html'
})
export class PatientUpdateComponent implements OnInit {
    patient: IPatient;
    isSaving: boolean;

    patienteles: IPatientele[];

    assures: IAssure[];
    dateNaissanceDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private patientService: PatientService,
        private patienteleService: PatienteleService,
        private assureService: AssureService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ patient }) => {
            this.patient = patient;
        });
        this.patienteleService.query().subscribe(
            (res: HttpResponse<IPatientele[]>) => {
                this.patienteles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.assureService.query({ filter: 'patient-is-null' }).subscribe(
            (res: HttpResponse<IAssure[]>) => {
                if (!this.patient.assureId) {
                    this.assures = res.body;
                } else {
                    this.assureService.find(this.patient.assureId).subscribe(
                        (subRes: HttpResponse<IAssure>) => {
                            this.assures = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.patient.id !== undefined) {
            this.subscribeToSaveResponse(this.patientService.update(this.patient));
        } else {
            this.subscribeToSaveResponse(this.patientService.create(this.patient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>) {
        result.subscribe((res: HttpResponse<IPatient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPatienteleById(index: number, item: IPatientele) {
        return item.id;
    }

    trackAssureById(index: number, item: IAssure) {
        return item.id;
    }
}
