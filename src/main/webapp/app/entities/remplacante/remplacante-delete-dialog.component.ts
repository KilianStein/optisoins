import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRemplacante } from 'app/shared/model/remplacante.model';
import { RemplacanteService } from './remplacante.service';

@Component({
    selector: 'jhi-remplacante-delete-dialog',
    templateUrl: './remplacante-delete-dialog.component.html'
})
export class RemplacanteDeleteDialogComponent {
    remplacante: IRemplacante;

    constructor(
        private remplacanteService: RemplacanteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.remplacanteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'remplacanteListModification',
                content: 'Deleted an remplacante'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-remplacante-delete-popup',
    template: ''
})
export class RemplacanteDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ remplacante }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RemplacanteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.remplacante = remplacante;
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
