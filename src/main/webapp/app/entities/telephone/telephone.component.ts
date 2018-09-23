import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITelephone } from 'app/shared/model/telephone.model';
import { Principal } from 'app/core';
import { TelephoneService } from './telephone.service';

@Component({
    selector: 'jhi-telephone',
    templateUrl: './telephone.component.html'
})
export class TelephoneComponent implements OnInit, OnDestroy {
    telephones: ITelephone[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private telephoneService: TelephoneService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.telephoneService.query().subscribe(
            (res: HttpResponse<ITelephone[]>) => {
                this.telephones = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTelephones();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITelephone) {
        return item.id;
    }

    registerChangeInTelephones() {
        this.eventSubscriber = this.eventManager.subscribe('telephoneListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
