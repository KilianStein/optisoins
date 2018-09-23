import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrdonnance } from 'app/shared/model/ordonnance.model';
import { OrdonnanceService } from './ordonnance.service';

@Component({
    selector: 'jhi-ordonnance-delete-dialog',
    templateUrl: './ordonnance-delete-dialog.component.html'
})
export class OrdonnanceDeleteDialogComponent {
    ordonnance: IOrdonnance;

    constructor(private ordonnanceService: OrdonnanceService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ordonnanceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ordonnanceListModification',
                content: 'Deleted an ordonnance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ordonnance-delete-popup',
    template: ''
})
export class OrdonnanceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ordonnance }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrdonnanceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.ordonnance = ordonnance;
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
