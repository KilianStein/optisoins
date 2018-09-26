import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEtatRecapitulatif } from 'app/shared/model/etat-recapitulatif.model';
import { EtatRecapitulatifService } from './etat-recapitulatif.service';

@Component({
    selector: 'jhi-etat-recapitulatif-delete-dialog',
    templateUrl: './etat-recapitulatif-delete-dialog.component.html'
})
export class EtatRecapitulatifDeleteDialogComponent {
    etatRecapitulatif: IEtatRecapitulatif;

    constructor(
        private etatRecapitulatifService: EtatRecapitulatifService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.etatRecapitulatifService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'etatRecapitulatifListModification',
                content: 'Deleted an etatRecapitulatif'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-etat-recapitulatif-delete-popup',
    template: ''
})
export class EtatRecapitulatifDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ etatRecapitulatif }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(EtatRecapitulatifDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.etatRecapitulatif = etatRecapitulatif;
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
