import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompteBancaire } from 'app/shared/model/compte-bancaire.model';
import { Principal } from 'app/core';
import { CompteBancaireService } from './compte-bancaire.service';

@Component({
    selector: 'jhi-compte-bancaire',
    templateUrl: './compte-bancaire.component.html'
})
export class CompteBancaireComponent implements OnInit, OnDestroy {
    compteBancaires: ICompteBancaire[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private compteBancaireService: CompteBancaireService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.compteBancaireService.query().subscribe(
            (res: HttpResponse<ICompteBancaire[]>) => {
                this.compteBancaires = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCompteBancaires();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICompteBancaire) {
        return item.id;
    }

    registerChangeInCompteBancaires() {
        this.eventSubscriber = this.eventManager.subscribe('compteBancaireListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
