import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';
import { OrthophonisteService } from './orthophoniste.service';

@Component({
    selector: 'jhi-orthophoniste-delete-dialog',
    templateUrl: './orthophoniste-delete-dialog.component.html'
})
export class OrthophonisteDeleteDialogComponent {
    orthophoniste: IOrthophoniste;

    constructor(
        private orthophonisteService: OrthophonisteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orthophonisteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'orthophonisteListModification',
                content: 'Deleted an orthophoniste'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-orthophoniste-delete-popup',
    template: ''
})
export class OrthophonisteDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orthophoniste }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OrthophonisteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.orthophoniste = orthophoniste;
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
