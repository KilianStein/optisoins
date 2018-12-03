import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';
import { Principal } from 'app/core';
import { DemandeEntentePrealableService } from './demande-entente-prealable.service';

@Component({
    selector: 'jhi-demande-entente-prealable',
    templateUrl: './demande-entente-prealable.component.html'
})
export class DemandeEntentePrealableComponent implements OnInit, OnDestroy {
    demandeEntentePrealables: IDemandeEntentePrealable[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private demandeEntentePrealableService: DemandeEntentePrealableService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.demandeEntentePrealableService.query().subscribe(
            (res: HttpResponse<IDemandeEntentePrealable[]>) => {
                this.demandeEntentePrealables = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDemandeEntentePrealables();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDemandeEntentePrealable) {
        return item.id;
    }

    registerChangeInDemandeEntentePrealables() {
        this.eventSubscriber = this.eventManager.subscribe('demandeEntentePrealableListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
