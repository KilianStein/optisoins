import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMutuelle } from 'app/shared/model/mutuelle.model';
import { MutuelleService } from './mutuelle.service';
import { IAssure } from 'app/shared/model/assure.model';
import { AssureService } from 'app/entities/assure';

@Component({
    selector: 'jhi-mutuelle-update',
    templateUrl: './mutuelle-update.component.html'
})
export class MutuelleUpdateComponent implements OnInit {
    private _mutuelle: IMutuelle;
    isSaving: boolean;

    assures: IAssure[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private mutuelleService: MutuelleService,
        private assureService: AssureService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mutuelle }) => {
            this.mutuelle = mutuelle;
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
        if (this.mutuelle.id !== undefined) {
            this.subscribeToSaveResponse(this.mutuelleService.update(this.mutuelle));
        } else {
            this.subscribeToSaveResponse(this.mutuelleService.create(this.mutuelle));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMutuelle>>) {
        result.subscribe((res: HttpResponse<IMutuelle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get mutuelle() {
        return this._mutuelle;
    }

    set mutuelle(mutuelle: IMutuelle) {
        this._mutuelle = mutuelle;
    }
}
