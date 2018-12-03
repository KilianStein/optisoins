import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICompteBancaire } from 'app/shared/model/compte-bancaire.model';
import { CompteBancaireService } from './compte-bancaire.service';

@Component({
    selector: 'jhi-compte-bancaire-update',
    templateUrl: './compte-bancaire-update.component.html'
})
export class CompteBancaireUpdateComponent implements OnInit {
    compteBancaire: ICompteBancaire;
    isSaving: boolean;

    constructor(private compteBancaireService: CompteBancaireService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compteBancaire }) => {
            this.compteBancaire = compteBancaire;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.compteBancaire.id !== undefined) {
            this.subscribeToSaveResponse(this.compteBancaireService.update(this.compteBancaire));
        } else {
            this.subscribeToSaveResponse(this.compteBancaireService.create(this.compteBancaire));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICompteBancaire>>) {
        result.subscribe((res: HttpResponse<ICompteBancaire>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
