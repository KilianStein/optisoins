import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAmo } from 'app/shared/model/amo.model';
import { Principal } from 'app/core';
import { AmoService } from './amo.service';

@Component({
    selector: 'jhi-amo',
    templateUrl: './amo.component.html'
})
export class AmoComponent implements OnInit, OnDestroy {
    amos: IAmo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private amoService: AmoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.amoService.query().subscribe(
            (res: HttpResponse<IAmo[]>) => {
                this.amos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAmos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAmo) {
        return item.id;
    }

    registerChangeInAmos() {
        this.eventSubscriber = this.eventManager.subscribe('amoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
