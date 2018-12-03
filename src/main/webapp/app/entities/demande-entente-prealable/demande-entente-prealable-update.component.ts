import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';
import { DemandeEntentePrealableService } from './demande-entente-prealable.service';
import { IOrdonnance } from 'app/shared/model/ordonnance.model';
import { OrdonnanceService } from 'app/entities/ordonnance';
import { IAmo } from 'app/shared/model/amo.model';
import { AmoService } from 'app/entities/amo';

@Component({
    selector: 'jhi-demande-entente-prealable-update',
    templateUrl: './demande-entente-prealable-update.component.html'
})
export class DemandeEntentePrealableUpdateComponent implements OnInit {
    demandeEntentePrealable: IDemandeEntentePrealable;
    isSaving: boolean;

    ordonnances: IOrdonnance[];

    amos: IAmo[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private demandeEntentePrealableService: DemandeEntentePrealableService,
        private ordonnanceService: OrdonnanceService,
        private amoService: AmoService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ demandeEntentePrealable }) => {
            this.demandeEntentePrealable = demandeEntentePrealable;
        });
        this.ordonnanceService.query().subscribe(
            (res: HttpResponse<IOrdonnance[]>) => {
                this.ordonnances = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.amoService.query({ filter: 'demandeententeprealable-is-null' }).subscribe(
            (res: HttpResponse<IAmo[]>) => {
                if (!this.demandeEntentePrealable.amoId) {
                    this.amos = res.body;
                } else {
                    this.amoService.find(this.demandeEntentePrealable.amoId).subscribe(
                        (subRes: HttpResponse<IAmo>) => {
                            this.amos = [subRes.body].concat(res.body);
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
        if (this.demandeEntentePrealable.id !== undefined) {
            this.subscribeToSaveResponse(this.demandeEntentePrealableService.update(this.demandeEntentePrealable));
        } else {
            this.subscribeToSaveResponse(this.demandeEntentePrealableService.create(this.demandeEntentePrealable));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDemandeEntentePrealable>>) {
        result.subscribe(
            (res: HttpResponse<IDemandeEntentePrealable>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackOrdonnanceById(index: number, item: IOrdonnance) {
        return item.id;
    }

    trackAmoById(index: number, item: IAmo) {
        return item.id;
    }
}
