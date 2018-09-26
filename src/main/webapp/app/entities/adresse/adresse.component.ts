import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAdresse } from 'app/shared/model/adresse.model';
import { Principal } from 'app/core';
import { AdresseService } from './adresse.service';

@Component({
    selector: 'jhi-adresse',
    templateUrl: './adresse.component.html'
})
export class AdresseComponent implements OnInit, OnDestroy {
    adresses: IAdresse[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private adresseService: AdresseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.adresseService.query().subscribe(
            (res: HttpResponse<IAdresse[]>) => {
                this.adresses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAdresses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAdresse) {
        return item.id;
    }

    registerChangeInAdresses() {
        this.eventSubscriber = this.eventManager.subscribe('adresseListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
