import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMedecin } from 'app/shared/model/medecin.model';
import { MedecinService } from './medecin.service';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse';
import { ICourriel } from 'app/shared/model/courriel.model';
import { CourrielService } from 'app/entities/courriel';
import { ITelephone } from 'app/shared/model/telephone.model';
import { TelephoneService } from 'app/entities/telephone';

@Component({
    selector: 'jhi-medecin-update',
    templateUrl: './medecin-update.component.html'
})
export class MedecinUpdateComponent implements OnInit {
    private _medecin: IMedecin;
    isSaving: boolean;

    adresses: IAdresse[];

    courriels: ICourriel[];

    telephones: ITelephone[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private medecinService: MedecinService,
        private adresseService: AdresseService,
        private courrielService: CourrielService,
        private telephoneService: TelephoneService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ medecin }) => {
            this.medecin = medecin;
        });
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
        this.telephoneService.query().subscribe(
            (res: HttpResponse<ITelephone[]>) => {
                this.telephones = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.medecin.id !== undefined) {
            this.subscribeToSaveResponse(this.medecinService.update(this.medecin));
        } else {
            this.subscribeToSaveResponse(this.medecinService.create(this.medecin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMedecin>>) {
        result.subscribe((res: HttpResponse<IMedecin>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAdresseById(index: number, item: IAdresse) {
        return item.id;
    }

    trackCourrielById(index: number, item: ICourriel) {
        return item.id;
    }

    trackTelephoneById(index: number, item: ITelephone) {
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
    get medecin() {
        return this._medecin;
    }

    set medecin(medecin: IMedecin) {
        this._medecin = medecin;
    }
}
