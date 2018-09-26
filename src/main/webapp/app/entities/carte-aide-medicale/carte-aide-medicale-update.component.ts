import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';
import { CarteAideMedicaleService } from './carte-aide-medicale.service';
import { IAssure } from 'app/shared/model/assure.model';
import { AssureService } from 'app/entities/assure';

@Component({
    selector: 'jhi-carte-aide-medicale-update',
    templateUrl: './carte-aide-medicale-update.component.html'
})
export class CarteAideMedicaleUpdateComponent implements OnInit {
    private _carteAideMedicale: ICarteAideMedicale;
    isSaving: boolean;

    assures: IAssure[];
    dateDebutValiditeDp: any;
    dateFinValiditeDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private carteAideMedicaleService: CarteAideMedicaleService,
        private assureService: AssureService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ carteAideMedicale }) => {
            this.carteAideMedicale = carteAideMedicale;
        });
        this.assureService.query().subscribe(
            (res: HttpResponse<IAssure[]>) => {
                this.assures = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.carteAideMedicale.id !== undefined) {
            this.subscribeToSaveResponse(this.carteAideMedicaleService.update(this.carteAideMedicale));
        } else {
            this.subscribeToSaveResponse(this.carteAideMedicaleService.create(this.carteAideMedicale));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICarteAideMedicale>>) {
        result.subscribe((res: HttpResponse<ICarteAideMedicale>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAssureById(index: number, item: IAssure) {
        return item.id;
    }
    get carteAideMedicale() {
        return this._carteAideMedicale;
    }

    set carteAideMedicale(carteAideMedicale: ICarteAideMedicale) {
        this._carteAideMedicale = carteAideMedicale;
    }
}
