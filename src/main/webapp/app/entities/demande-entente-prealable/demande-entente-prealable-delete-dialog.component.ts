import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';
import { DemandeEntentePrealableService } from './demande-entente-prealable.service';

@Component({
    selector: 'jhi-demande-entente-prealable-delete-dialog',
    templateUrl: './demande-entente-prealable-delete-dialog.component.html'
})
export class DemandeEntentePrealableDeleteDialogComponent {
    demandeEntentePrealable: IDemandeEntentePrealable;

    constructor(
        private demandeEntentePrealableService: DemandeEntentePrealableService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.demandeEntentePrealableService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'demandeEntentePrealableListModification',
                content: 'Deleted an demandeEntentePrealable'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-demande-entente-prealable-delete-popup',
    template: ''
})
export class DemandeEntentePrealableDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ demandeEntentePrealable }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DemandeEntentePrealableDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.demandeEntentePrealable = demandeEntentePrealable;
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
