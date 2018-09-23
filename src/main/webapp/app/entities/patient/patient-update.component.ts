import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPatient } from 'app/shared/model/patient.model';
import { PatientService } from './patient.service';
import { IPatientele } from 'app/shared/model/patientele.model';
import { PatienteleService } from 'app/entities/patientele';
import { IAssure } from 'app/shared/model/assure.model';
import { AssureService } from 'app/entities/assure';
import { ITelephone } from 'app/shared/model/telephone.model';
import { TelephoneService } from 'app/entities/telephone';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse';
import { ICourriel } from 'app/shared/model/courriel.model';
import { CourrielService } from 'app/entities/courriel';

@Component({
    selector: 'jhi-patient-update',
    templateUrl: './patient-update.component.html'
})
export class PatientUpdateComponent implements OnInit {
    private _patient: IPatient;
    isSaving: boolean;

    patienteles: IPatientele[];

    assures: IAssure[];

    telephones: ITelephone[];

    adresses: IAdresse[];

    courriels: ICourriel[];
    dateNaissanceDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private patientService: PatientService,
        private patienteleService: PatienteleService,
        private assureService: AssureService,
        private telephoneService: TelephoneService,
        private adresseService: AdresseService,
        private courrielService: CourrielService,
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
        this.telephoneService.query().subscribe(
            (res: HttpResponse<ITelephone[]>) => {
                this.telephones = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.adresseService.query().subscribe(
            (res: HttpResponse<IAdresse[]>) => {
                this.adresses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.courrielService.query().subscribe(
            (res: HttpResponse<ICourriel[]>) => {
                this.courriels = res.body;
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

    trackTelephoneById(index: number, item: ITelephone) {
        return item.id;
    }

    trackAdresseById(index: number, item: IAdresse) {
        return item.id;
    }

    trackCourrielById(index: number, item: ICourriel) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get patient() {
        return this._patient;
    }

    set patient(patient: IPatient) {
        this._patient = patient;
    }
}
