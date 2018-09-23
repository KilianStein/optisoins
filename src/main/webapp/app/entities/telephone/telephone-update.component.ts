import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITelephone } from 'app/shared/model/telephone.model';
import { TelephoneService } from './telephone.service';

@Component({
    selector: 'jhi-telephone-update',
    templateUrl: './telephone-update.component.html'
})
export class TelephoneUpdateComponent implements OnInit {
    private _telephone: ITelephone;
    isSaving: boolean;

    constructor(private telephoneService: TelephoneService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ telephone }) => {
            this.telephone = telephone;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.telephone.id !== undefined) {
            this.subscribeToSaveResponse(this.telephoneService.update(this.telephone));
        } else {
            this.subscribeToSaveResponse(this.telephoneService.create(this.telephone));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITelephone>>) {
        result.subscribe((res: HttpResponse<ITelephone>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get telephone() {
        return this._telephone;
    }

    set telephone(telephone: ITelephone) {
        this._telephone = telephone;
    }
}
