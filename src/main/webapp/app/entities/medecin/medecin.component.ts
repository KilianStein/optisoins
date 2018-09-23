import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMedecin } from 'app/shared/model/medecin.model';
import { Principal } from 'app/core';
import { MedecinService } from './medecin.service';

@Component({
    selector: 'jhi-medecin',
    templateUrl: './medecin.component.html'
})
export class MedecinComponent implements OnInit, OnDestroy {
    medecins: IMedecin[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private medecinService: MedecinService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.medecinService.query().subscribe(
            (res: HttpResponse<IMedecin[]>) => {
                this.medecins = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMedecins();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMedecin) {
        return item.id;
    }

    registerChangeInMedecins() {
        this.eventSubscriber = this.eventManager.subscribe('medecinListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
