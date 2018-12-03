import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';
import { OrthophonisteService } from './orthophoniste.service';
import { ICompteBancaire } from 'app/shared/model/compte-bancaire.model';
import { CompteBancaireService } from 'app/entities/compte-bancaire';

@Component({
    selector: 'jhi-orthophoniste-update',
    templateUrl: './orthophoniste-update.component.html'
})
export class OrthophonisteUpdateComponent implements OnInit {
    orthophoniste: IOrthophoniste;
    isSaving: boolean;

    comptebancaires: ICompteBancaire[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private orthophonisteService: OrthophonisteService,
        private compteBancaireService: CompteBancaireService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orthophoniste }) => {
            this.orthophoniste = orthophoniste;
        });
        this.compteBancaireService.query({ filter: 'orthophoniste-is-null' }).subscribe(
            (res: HttpResponse<ICompteBancaire[]>) => {
                if (!this.orthophoniste.compteBancaireId) {
                    this.comptebancaires = res.body;
                } else {
                    this.compteBancaireService.find(this.orthophoniste.compteBancaireId).subscribe(
                        (subRes: HttpResponse<ICompteBancaire>) => {
                            this.comptebancaires = [subRes.body].concat(res.body);
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
        if (this.orthophoniste.id !== undefined) {
            this.subscribeToSaveResponse(this.orthophonisteService.update(this.orthophoniste));
        } else {
            this.subscribeToSaveResponse(this.orthophonisteService.create(this.orthophoniste));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrthophoniste>>) {
        result.subscribe((res: HttpResponse<IOrthophoniste>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCompteBancaireById(index: number, item: ICompteBancaire) {
        return item.id;
    }
}
