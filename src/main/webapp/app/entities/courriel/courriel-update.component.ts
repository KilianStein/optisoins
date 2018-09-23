import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICourriel } from 'app/shared/model/courriel.model';
import { CourrielService } from './courriel.service';

@Component({
    selector: 'jhi-courriel-update',
    templateUrl: './courriel-update.component.html'
})
export class CourrielUpdateComponent implements OnInit {
    private _courriel: ICourriel;
    isSaving: boolean;

    constructor(private courrielService: CourrielService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ courriel }) => {
            this.courriel = courriel;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.courriel.id !== undefined) {
            this.subscribeToSaveResponse(this.courrielService.update(this.courriel));
        } else {
            this.subscribeToSaveResponse(this.courrielService.create(this.courriel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourriel>>) {
        result.subscribe((res: HttpResponse<ICourriel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get courriel() {
        return this._courriel;
    }

    set courriel(courriel: ICourriel) {
        this._courriel = courriel;
    }
}
