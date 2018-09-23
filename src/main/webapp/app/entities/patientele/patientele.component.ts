import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPatientele } from 'app/shared/model/patientele.model';
import { Principal } from 'app/core';
import { PatienteleService } from './patientele.service';

@Component({
    selector: 'jhi-patientele',
    templateUrl: './patientele.component.html'
})
export class PatienteleComponent implements OnInit, OnDestroy {
    patienteles: IPatientele[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private patienteleService: PatienteleService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.patienteleService.query().subscribe(
            (res: HttpResponse<IPatientele[]>) => {
                this.patienteles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPatienteles();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPatientele) {
        return item.id;
    }

    registerChangeInPatienteles() {
        this.eventSubscriber = this.eventManager.subscribe('patienteleListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
