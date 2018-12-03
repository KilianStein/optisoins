import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPriseEnCharge } from 'app/shared/model/prise-en-charge.model';
import { Principal } from 'app/core';
import { PriseEnChargeService } from './prise-en-charge.service';

@Component({
    selector: 'jhi-prise-en-charge',
    templateUrl: './prise-en-charge.component.html'
})
export class PriseEnChargeComponent implements OnInit, OnDestroy {
    priseEnCharges: IPriseEnCharge[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private priseEnChargeService: PriseEnChargeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.priseEnChargeService.query().subscribe(
            (res: HttpResponse<IPriseEnCharge[]>) => {
                this.priseEnCharges = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPriseEnCharges();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPriseEnCharge) {
        return item.id;
    }

    registerChangeInPriseEnCharges() {
        this.eventSubscriber = this.eventManager.subscribe('priseEnChargeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
