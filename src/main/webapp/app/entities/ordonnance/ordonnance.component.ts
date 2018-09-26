import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrdonnance } from 'app/shared/model/ordonnance.model';
import { Principal } from 'app/core';
import { OrdonnanceService } from './ordonnance.service';

@Component({
    selector: 'jhi-ordonnance',
    templateUrl: './ordonnance.component.html'
})
export class OrdonnanceComponent implements OnInit, OnDestroy {
    ordonnances: IOrdonnance[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ordonnanceService: OrdonnanceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.ordonnanceService.query().subscribe(
            (res: HttpResponse<IOrdonnance[]>) => {
                this.ordonnances = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOrdonnances();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOrdonnance) {
        return item.id;
    }

    registerChangeInOrdonnances() {
        this.eventSubscriber = this.eventManager.subscribe('ordonnanceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
