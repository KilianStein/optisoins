import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEtatRecapitulatif } from 'app/shared/model/etat-recapitulatif.model';
import { EtatRecapitulatifService } from './etat-recapitulatif.service';
import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';
import { OrthophonisteService } from 'app/entities/orthophoniste';

@Component({
    selector: 'jhi-etat-recapitulatif-update',
    templateUrl: './etat-recapitulatif-update.component.html'
})
export class EtatRecapitulatifUpdateComponent implements OnInit {
    etatRecapitulatif: IEtatRecapitulatif;
    isSaving: boolean;

    orthophonistes: IOrthophoniste[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private etatRecapitulatifService: EtatRecapitulatifService,
        private orthophonisteService: OrthophonisteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ etatRecapitulatif }) => {
            this.etatRecapitulatif = etatRecapitulatif;
        });
        this.orthophonisteService.query().subscribe(
            (res: HttpResponse<IOrthophoniste[]>) => {
                this.orthophonistes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.etatRecapitulatif.id !== undefined) {
            this.subscribeToSaveResponse(this.etatRecapitulatifService.update(this.etatRecapitulatif));
        } else {
            this.subscribeToSaveResponse(this.etatRecapitulatifService.create(this.etatRecapitulatif));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEtatRecapitulatif>>) {
        result.subscribe((res: HttpResponse<IEtatRecapitulatif>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
