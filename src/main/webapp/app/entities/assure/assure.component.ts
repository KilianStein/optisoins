import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAssure } from 'app/shared/model/assure.model';
import { Principal } from 'app/core';
import { AssureService } from './assure.service';

@Component({
    selector: 'jhi-assure',
    templateUrl: './assure.component.html'
})
export class AssureComponent implements OnInit, OnDestroy {
    assures: IAssure[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private assureService: AssureService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.assureService.query().subscribe(
            (res: HttpResponse<IAssure[]>) => {
                this.assures = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAssures();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAssure) {
        return item.id;
    }

    registerChangeInAssures() {
        this.eventSubscriber = this.eventManager.subscribe('assureListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
