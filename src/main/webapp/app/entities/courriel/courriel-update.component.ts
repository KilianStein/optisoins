import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICourriel } from 'app/shared/model/courriel.model';
import { CourrielService } from './courriel.service';
import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';
import { OrthophonisteService } from 'app/entities/orthophoniste';
import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from 'app/entities/patient';
import { IMedecin } from 'app/shared/model/medecin.model';
import { MedecinService } from 'app/entities/medecin';

@Component({
    selector: 'jhi-courriel-update',
    templateUrl: './courriel-update.component.html'
})
export class CourrielUpdateComponent implements OnInit {
    courriel: ICourriel;
    isSaving: boolean;

    orthophonistes: IOrthophoniste[];

    patients: IPatient[];

    medecins: IMedecin[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private courrielService: CourrielService,
        private orthophonisteService: OrthophonisteService,
        private patientService: PatientService,
        private medecinService: MedecinService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courriel }) => {
            this.courriel = courriel;
        });
        this.orthophonisteService.query().subscribe(
            (res: HttpResponse<IOrthophoniste[]>) => {
                this.orthophonistes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.patientService.query().subscribe(
            (res: HttpResponse<IPatient[]>) => {
                this.patients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.medecinService.query().subscribe(
            (res: HttpResponse<IMedecin[]>) => {
                this.medecins = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courriel.id !== undefined) {
            this.subscribeToSaveResponse(this.courrielService.update(this.courriel));
        } else {
            this.subscribeToSaveResponse(this.courrielService.create(this.courriel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourriel>>) {
        result.subscribe((res: HttpResponse<ICourriel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPatientById(index: number, item: IPatient) {
        return item.id;
    }

    trackMedecinById(index: number, item: IMedecin) {
        return item.id;
    }
}
