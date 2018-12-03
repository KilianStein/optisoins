import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICourriel } from 'app/shared/model/courriel.model';
import { Principal } from 'app/core';
import { CourrielService } from './courriel.service';

@Component({
    selector: 'jhi-courriel',
    templateUrl: './courriel.component.html'
})
export class CourrielComponent implements OnInit, OnDestroy {
    courriels: ICourriel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private courrielService: CourrielService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.courrielService.query().subscribe(
            (res: HttpResponse<ICourriel[]>) => {
                this.courriels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCourriels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICourriel) {
        return item.id;
    }

    registerChangeInCourriels() {
        this.eventSubscriber = this.eventManager.subscribe('courrielListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
