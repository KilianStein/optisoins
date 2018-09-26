import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompteBancaire } from 'app/shared/model/compte-bancaire.model';
import { CompteBancaireService } from './compte-bancaire.service';

@Component({
    selector: 'jhi-compte-bancaire-delete-dialog',
    templateUrl: './compte-bancaire-delete-dialog.component.html'
})
export class CompteBancaireDeleteDialogComponent {
    compteBancaire: ICompteBancaire;

    constructor(
        private compteBancaireService: CompteBancaireService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.compteBancaireService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'compteBancaireListModification',
                content: 'Deleted an compteBancaire'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-compte-bancaire-delete-popup',
    template: ''
})
export class CompteBancaireDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compteBancaire }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompteBancaireDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.compteBancaire = compteBancaire;
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
