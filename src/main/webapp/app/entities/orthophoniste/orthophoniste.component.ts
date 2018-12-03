import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';
import { Principal } from 'app/core';
import { OrthophonisteService } from './orthophoniste.service';

@Component({
    selector: 'jhi-orthophoniste',
    templateUrl: './orthophoniste.component.html'
})
export class OrthophonisteComponent implements OnInit, OnDestroy {
    orthophonistes: IOrthophoniste[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private orthophonisteService: OrthophonisteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.orthophonisteService.query().subscribe(
            (res: HttpResponse<IOrthophoniste[]>) => {
                this.orthophonistes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOrthophonistes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOrthophoniste) {
        return item.id;
    }

    registerChangeInOrthophonistes() {
        this.eventSubscriber = this.eventManager.subscribe('orthophonisteListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
