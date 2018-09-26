import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISeance } from 'app/shared/model/seance.model';
import { SeanceService } from './seance.service';

@Component({
    selector: 'jhi-seance-delete-dialog',
    templateUrl: './seance-delete-dialog.component.html'
})
export class SeanceDeleteDialogComponent {
    seance: ISeance;

    constructor(private seanceService: SeanceService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seanceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'seanceListModification',
                content: 'Deleted an seance'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-seance-delete-popup',
    template: ''
})
export class SeanceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ seance }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SeanceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.seance = seance;
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
