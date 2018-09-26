import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPatientele } from 'app/shared/model/patientele.model';
import { PatienteleService } from './patientele.service';
import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';
import { OrthophonisteService } from 'app/entities/orthophoniste';

@Component({
    selector: 'jhi-patientele-update',
    templateUrl: './patientele-update.component.html'
})
export class PatienteleUpdateComponent implements OnInit {
    private _patientele: IPatientele;
    isSaving: boolean;

    orthophonistes: IOrthophoniste[];

    titulaires: IOrthophoniste[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private patienteleService: PatienteleService,
        private orthophonisteService: OrthophonisteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ patientele }) => {
            this.patientele = patientele;
        });
        this.orthophonisteService.query().subscribe(
            (res: HttpResponse<IOrthophoniste[]>) => {
                this.orthophonistes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.orthophonisteService.query({ filter: 'patientele-is-null' }).subscribe(
            (res: HttpResponse<IOrthophoniste[]>) => {
                if (!this.patientele.titulaireId) {
                    this.titulaires = res.body;
                } else {
                    this.orthophonisteService.find(this.patientele.titulaireId).subscribe(
                        (subRes: HttpResponse<IOrthophoniste>) => {
                            this.titulaires = [subRes.body].concat(res.body);
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
        if (this.patientele.id !== undefined) {
            this.subscribeToSaveResponse(this.patienteleService.update(this.patientele));
        } else {
            this.subscribeToSaveResponse(this.patienteleService.create(this.patientele));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPatientele>>) {
        result.subscribe((res: HttpResponse<IPatientele>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrthophonisteById(index: number, item: IOrthophoniste) {
        return item.id;
    }
    get patientele() {
        return this._patientele;
    }

    set patientele(patientele: IPatientele) {
        this._patientele = patientele;
    }
}
