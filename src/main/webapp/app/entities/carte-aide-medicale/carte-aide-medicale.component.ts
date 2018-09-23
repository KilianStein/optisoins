import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';
import { Principal } from 'app/core';
import { CarteAideMedicaleService } from './carte-aide-medicale.service';

@Component({
    selector: 'jhi-carte-aide-medicale',
    templateUrl: './carte-aide-medicale.component.html'
})
export class CarteAideMedicaleComponent implements OnInit, OnDestroy {
    carteAideMedicales: ICarteAideMedicale[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private carteAideMedicaleService: CarteAideMedicaleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.carteAideMedicaleService.query().subscribe(
            (res: HttpResponse<ICarteAideMedicale[]>) => {
                this.carteAideMedicales = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCarteAideMedicales();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICarteAideMedicale) {
        return item.id;
    }

    registerChangeInCarteAideMedicales() {
        this.eventSubscriber = this.eventManager.subscribe('carteAideMedicaleListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
