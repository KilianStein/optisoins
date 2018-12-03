import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFeuilleSoins } from 'app/shared/model/feuille-soins.model';
import { Principal } from 'app/core';
import { FeuilleSoinsService } from './feuille-soins.service';

@Component({
    selector: 'jhi-feuille-soins',
    templateUrl: './feuille-soins.component.html'
})
export class FeuilleSoinsComponent implements OnInit, OnDestroy {
    feuilleSoins: IFeuilleSoins[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private feuilleSoinsService: FeuilleSoinsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.feuilleSoinsService.query().subscribe(
            (res: HttpResponse<IFeuilleSoins[]>) => {
                this.feuilleSoins = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFeuilleSoins();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFeuilleSoins) {
        return item.id;
    }

    registerChangeInFeuilleSoins() {
        this.eventSubscriber = this.eventManager.subscribe('feuilleSoinsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
