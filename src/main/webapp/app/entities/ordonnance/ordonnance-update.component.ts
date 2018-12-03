import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IOrdonnance } from 'app/shared/model/ordonnance.model';
import { OrdonnanceService } from './ordonnance.service';
import { IMedecin } from 'app/shared/model/medecin.model';
import { MedecinService } from 'app/entities/medecin';
import { IPriseEnCharge } from 'app/shared/model/prise-en-charge.model';
import { PriseEnChargeService } from 'app/entities/prise-en-charge';

@Component({
    selector: 'jhi-ordonnance-update',
    templateUrl: './ordonnance-update.component.html'
})
export class OrdonnanceUpdateComponent implements OnInit {
    ordonnance: IOrdonnance;
    isSaving: boolean;

    medecins: IMedecin[];

    priseencharges: IPriseEnCharge[];
    datePrescriptionDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private ordonnanceService: OrdonnanceService,
        private medecinService: MedecinService,
        private priseEnChargeService: PriseEnChargeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ordonnance }) => {
            this.ordonnance = ordonnance;
        });
        this.medecinService.query({ filter: 'ordonnance-is-null' }).subscribe(
            (res: HttpResponse<IMedecin[]>) => {
                if (!this.ordonnance.medecinId) {
                    this.medecins = res.body;
                } else {
                    this.medecinService.find(this.ordonnance.medecinId).subscribe(
                        (subRes: HttpResponse<IMedecin>) => {
                            this.medecins = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.priseEnChargeService.query().subscribe(
            (res: HttpResponse<IPriseEnCharge[]>) => {
                this.priseencharges = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.ordonnance.id !== undefined) {
            this.subscribeToSaveResponse(this.ordonnanceService.update(this.ordonnance));
        } else {
            this.subscribeToSaveResponse(this.ordonnanceService.create(this.ordonnance));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrdonnance>>) {
        result.subscribe((res: HttpResponse<IOrdonnance>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMedecinById(index: number, item: IMedecin) {
        return item.id;
    }

    trackPriseEnChargeById(index: number, item: IPriseEnCharge) {
        return item.id;
    }
}
