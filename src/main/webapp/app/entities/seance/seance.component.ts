import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISeance } from 'app/shared/model/seance.model';
import { Principal } from 'app/core';
import { SeanceService } from './seance.service';

@Component({
    selector: 'jhi-seance',
    templateUrl: './seance.component.html'
})
export class SeanceComponent implements OnInit, OnDestroy {
    seances: ISeance[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seanceService: SeanceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.seanceService.query().subscribe(
            (res: HttpResponse<ISeance[]>) => {
                this.seances = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSeances();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISeance) {
        return item.id;
    }

    registerChangeInSeances() {
        this.eventSubscriber = this.eventManager.subscribe('seanceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
