import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMedecin } from 'app/shared/model/medecin.model';
import { MedecinService } from './medecin.service';

@Component({
    selector: 'jhi-medecin-update',
    templateUrl: './medecin-update.component.html'
})
export class MedecinUpdateComponent implements OnInit {
    medecin: IMedecin;
    isSaving: boolean;

    constructor(private medecinService: MedecinService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ medecin }) => {
            this.medecin = medecin;
        });
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
}
