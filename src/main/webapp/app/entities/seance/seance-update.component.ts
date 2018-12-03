import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ISeance } from 'app/shared/model/seance.model';
import { SeanceService } from './seance.service';
import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';
import { OrthophonisteService } from 'app/entities/orthophoniste';
import { IPriseEnCharge } from 'app/shared/model/prise-en-charge.model';
import { PriseEnChargeService } from 'app/entities/prise-en-charge';
import { IFeuilleSoins } from 'app/shared/model/feuille-soins.model';
import { FeuilleSoinsService } from 'app/entities/feuille-soins';

@Component({
    selector: 'jhi-seance-update',
    templateUrl: './seance-update.component.html'
})
export class SeanceUpdateComponent implements OnInit {
    seance: ISeance;
    isSaving: boolean;

    orthophonistes: IOrthophoniste[];

    priseencharges: IPriseEnCharge[];

    feuillesoins: IFeuilleSoins[];
    dateDebut: string;
    dateFin: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private seanceService: SeanceService,
        private orthophonisteService: OrthophonisteService,
        private priseEnChargeService: PriseEnChargeService,
        private feuilleSoinsService: FeuilleSoinsService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ seance }) => {
            this.seance = seance;
            this.dateDebut = this.seance.dateDebut != null ? this.seance.dateDebut.format(DATE_TIME_FORMAT) : null;
            this.dateFin = this.seance.dateFin != null ? this.seance.dateFin.format(DATE_TIME_FORMAT) : null;
        });
        this.orthophonisteService.query({ filter: 'seance-is-null' }).subscribe(
            (res: HttpResponse<IOrthophoniste[]>) => {
                if (!this.seance.orthophonisteId) {
                    this.orthophonistes = res.body;
                } else {
                    this.orthophonisteService.find(this.seance.orthophonisteId).subscribe(
                        (subRes: HttpResponse<IOrthophoniste>) => {
                            this.orthophonistes = [subRes.body].concat(res.body);
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
        this.feuilleSoinsService.query().subscribe(
            (res: HttpResponse<IFeuilleSoins[]>) => {
                this.feuillesoins = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.seance.dateDebut = this.dateDebut != null ? moment(this.dateDebut, DATE_TIME_FORMAT) : null;
        this.seance.dateFin = this.dateFin != null ? moment(this.dateFin, DATE_TIME_FORMAT) : null;
        if (this.seance.id !== undefined) {
            this.subscribeToSaveResponse(this.seanceService.update(this.seance));
        } else {
            this.subscribeToSaveResponse(this.seanceService.create(this.seance));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISeance>>) {
        result.subscribe((res: HttpResponse<ISeance>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPriseEnChargeById(index: number, item: IPriseEnCharge) {
        return item.id;
    }

    trackFeuilleSoinsById(index: number, item: IFeuilleSoins) {
        return item.id;
    }
}
