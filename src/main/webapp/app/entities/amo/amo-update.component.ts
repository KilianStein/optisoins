import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAmo } from 'app/shared/model/amo.model';
import { AmoService } from './amo.service';

@Component({
    selector: 'jhi-amo-update',
    templateUrl: './amo-update.component.html'
})
export class AmoUpdateComponent implements OnInit {
    amo: IAmo;
    isSaving: boolean;

    constructor(private amoService: AmoService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ amo }) => {
            this.amo = amo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.amo.id !== undefined) {
            this.subscribeToSaveResponse(this.amoService.update(this.amo));
        } else {
            this.subscribeToSaveResponse(this.amoService.create(this.amo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAmo>>) {
        result.subscribe((res: HttpResponse<IAmo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
