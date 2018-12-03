import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IRemplacante } from 'app/shared/model/remplacante.model';
import { RemplacanteService } from './remplacante.service';
import { IPatientele } from 'app/shared/model/patientele.model';
import { PatienteleService } from 'app/entities/patientele';
import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';
import { OrthophonisteService } from 'app/entities/orthophoniste';

@Component({
    selector: 'jhi-remplacante-update',
    templateUrl: './remplacante-update.component.html'
})
export class RemplacanteUpdateComponent implements OnInit {
    remplacante: IRemplacante;
    isSaving: boolean;

    patienteles: IPatientele[];

    orthophonistes: IOrthophoniste[];
    dateDebutDp: any;
    dateFinDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private remplacanteService: RemplacanteService,
        private patienteleService: PatienteleService,
        private orthophonisteService: OrthophonisteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ remplacante }) => {
            this.remplacante = remplacante;
        });
        this.patienteleService.query().subscribe(
            (res: HttpResponse<IPatientele[]>) => {
                this.patienteles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.orthophonisteService.query({ filter: 'remplacante-is-null' }).subscribe(
            (res: HttpResponse<IOrthophoniste[]>) => {
                if (!this.remplacante.orthophonisteId) {
                    this.orthophonistes = res.body;
                } else {
                    this.orthophonisteService.find(this.remplacante.orthophonisteId).subscribe(
                        (subRes: HttpResponse<IOrthophoniste>) => {
                            this.orthophonistes = [subRes.body].concat(res.body);
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
        if (this.remplacante.id !== undefined) {
            this.subscribeToSaveResponse(this.remplacanteService.update(this.remplacante));
        } else {
            this.subscribeToSaveResponse(this.remplacanteService.create(this.remplacante));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRemplacante>>) {
        result.subscribe((res: HttpResponse<IRemplacante>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrthophonisteById(index: number, item: IOrthophoniste) {
        return item.id;
    }
}
