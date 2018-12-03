import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFeuilleSoins } from 'app/shared/model/feuille-soins.model';
import { FeuilleSoinsService } from './feuille-soins.service';
import { IPriseEnCharge } from 'app/shared/model/prise-en-charge.model';
import { PriseEnChargeService } from 'app/entities/prise-en-charge';
import { IEtatRecapitulatif } from 'app/shared/model/etat-recapitulatif.model';
import { EtatRecapitulatifService } from 'app/entities/etat-recapitulatif';

@Component({
    selector: 'jhi-feuille-soins-update',
    templateUrl: './feuille-soins-update.component.html'
})
export class FeuilleSoinsUpdateComponent implements OnInit {
    feuilleSoins: IFeuilleSoins;
    isSaving: boolean;

    priseencharges: IPriseEnCharge[];

    etatrecapitulatifs: IEtatRecapitulatif[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private feuilleSoinsService: FeuilleSoinsService,
        private priseEnChargeService: PriseEnChargeService,
        private etatRecapitulatifService: EtatRecapitulatifService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ feuilleSoins }) => {
            this.feuilleSoins = feuilleSoins;
        });
        this.priseEnChargeService.query().subscribe(
            (res: HttpResponse<IPriseEnCharge[]>) => {
                this.priseencharges = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.etatRecapitulatifService.query().subscribe(
            (res: HttpResponse<IEtatRecapitulatif[]>) => {
                this.etatrecapitulatifs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.feuilleSoins.id !== undefined) {
            this.subscribeToSaveResponse(this.feuilleSoinsService.update(this.feuilleSoins));
        } else {
            this.subscribeToSaveResponse(this.feuilleSoinsService.create(this.feuilleSoins));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFeuilleSoins>>) {
        result.subscribe((res: HttpResponse<IFeuilleSoins>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPriseEnChargeById(index: number, item: IPriseEnCharge) {
        return item.id;
    }

    trackEtatRecapitulatifById(index: number, item: IEtatRecapitulatif) {
        return item.id;
    }
}
