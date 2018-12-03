import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPriseEnCharge } from 'app/shared/model/prise-en-charge.model';
import { PriseEnChargeService } from './prise-en-charge.service';

@Component({
    selector: 'jhi-prise-en-charge-delete-dialog',
    templateUrl: './prise-en-charge-delete-dialog.component.html'
})
export class PriseEnChargeDeleteDialogComponent {
    priseEnCharge: IPriseEnCharge;

    constructor(
        private priseEnChargeService: PriseEnChargeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.priseEnChargeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'priseEnChargeListModification',
                content: 'Deleted an priseEnCharge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prise-en-charge-delete-popup',
    template: ''
})
export class PriseEnChargeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ priseEnCharge }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PriseEnChargeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.priseEnCharge = priseEnCharge;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
