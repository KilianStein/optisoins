import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPriseEnCharge } from 'app/shared/model/prise-en-charge.model';
import { PriseEnChargeService } from './prise-en-charge.service';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';

@Component({
    selector: 'jhi-prise-en-charge-update',
    templateUrl: './prise-en-charge-update.component.html'
})
export class PriseEnChargeUpdateComponent implements OnInit {
    private _priseEnCharge: IPriseEnCharge;
    isSaving: boolean;

    patients: IPatient[];
    dateDebutDp: any;
    dateFinDp: any;
    accidentDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private priseEnChargeService: PriseEnChargeService,
        private patientService: PatientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ priseEnCharge }) => {
            this.priseEnCharge = priseEnCharge;
        });
        this.patientService.query().subscribe(
            (res: HttpResponse<IPatient[]>) => {
                this.patients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.priseEnCharge.id !== undefined) {
            this.subscribeToSaveResponse(this.priseEnChargeService.update(this.priseEnCharge));
        } else {
            this.subscribeToSaveResponse(this.priseEnChargeService.create(this.priseEnCharge));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPriseEnCharge>>) {
        result.subscribe((res: HttpResponse<IPriseEnCharge>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPatientById(index: number, item: IPatient) {
        return item.id;
    }
    get priseEnCharge() {
        return this._priseEnCharge;
    }

    set priseEnCharge(priseEnCharge: IPriseEnCharge) {
        this._priseEnCharge = priseEnCharge;
    }
}
