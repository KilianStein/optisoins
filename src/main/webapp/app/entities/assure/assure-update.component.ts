import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IAssure } from 'app/shared/model/assure.model';
import { AssureService } from './assure.service';
import { ICourriel } from 'app/shared/model/courriel.model';
import { CourrielService } from 'app/entities/courriel';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse';
import { ITelephone } from 'app/shared/model/telephone.model';
import { TelephoneService } from 'app/entities/telephone';

@Component({
    selector: 'jhi-assure-update',
    templateUrl: './assure-update.component.html'
})
export class AssureUpdateComponent implements OnInit {
    assure: IAssure;
    isSaving: boolean;

    courriels: ICourriel[];

    adresses: IAdresse[];

    telephones: ITelephone[];
    dateNaissanceDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private assureService: AssureService,
        private courrielService: CourrielService,
        private adresseService: AdresseService,
        private telephoneService: TelephoneService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ assure }) => {
            this.assure = assure;
        });
        this.courrielService.query({ filter: 'assure-is-null' }).subscribe(
            (res: HttpResponse<ICourriel[]>) => {
                if (!this.assure.courrielId) {
                    this.courriels = res.body;
                } else {
                    this.courrielService.find(this.assure.courrielId).subscribe(
                        (subRes: HttpResponse<ICourriel>) => {
                            this.courriels = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.adresseService.query({ filter: 'assure-is-null' }).subscribe(
            (res: HttpResponse<IAdresse[]>) => {
                if (!this.assure.adresseId) {
                    this.adresses = res.body;
                } else {
                    this.adresseService.find(this.assure.adresseId).subscribe(
                        (subRes: HttpResponse<IAdresse>) => {
                            this.adresses = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.telephoneService.query({ filter: 'assure-is-null' }).subscribe(
            (res: HttpResponse<ITelephone[]>) => {
                if (!this.assure.telephoneId) {
                    this.telephones = res.body;
                } else {
                    this.telephoneService.find(this.assure.telephoneId).subscribe(
                        (subRes: HttpResponse<ITelephone>) => {
                            this.telephones = [subRes.body].concat(res.body);
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
        if (this.assure.id !== undefined) {
            this.subscribeToSaveResponse(this.assureService.update(this.assure));
        } else {
            this.subscribeToSaveResponse(this.assureService.create(this.assure));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAssure>>) {
        result.subscribe((res: HttpResponse<IAssure>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCourrielById(index: number, item: ICourriel) {
        return item.id;
    }

    trackAdresseById(index: number, item: IAdresse) {
        return item.id;
    }

    trackTelephoneById(index: number, item: ITelephone) {
        return item.id;
    }
}
