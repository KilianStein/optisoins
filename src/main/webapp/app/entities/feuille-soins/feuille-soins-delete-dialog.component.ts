import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFeuilleSoins } from 'app/shared/model/feuille-soins.model';
import { FeuilleSoinsService } from './feuille-soins.service';

@Component({
    selector: 'jhi-feuille-soins-delete-dialog',
    templateUrl: './feuille-soins-delete-dialog.component.html'
})
export class FeuilleSoinsDeleteDialogComponent {
    feuilleSoins: IFeuilleSoins;

    constructor(
        private feuilleSoinsService: FeuilleSoinsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.feuilleSoinsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'feuilleSoinsListModification',
                content: 'Deleted an feuilleSoins'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-feuille-soins-delete-popup',
    template: ''
})
export class FeuilleSoinsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ feuilleSoins }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FeuilleSoinsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.feuilleSoins = feuilleSoins;
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
