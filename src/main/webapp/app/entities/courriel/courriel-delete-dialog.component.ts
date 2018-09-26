import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourriel } from 'app/shared/model/courriel.model';
import { CourrielService } from './courriel.service';

@Component({
    selector: 'jhi-courriel-delete-dialog',
    templateUrl: './courriel-delete-dialog.component.html'
})
export class CourrielDeleteDialogComponent {
    courriel: ICourriel;

    constructor(private courrielService: CourrielService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.courrielService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'courrielListModification',
                content: 'Deleted an courriel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-courriel-delete-popup',
    template: ''
})
export class CourrielDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courriel }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CourrielDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.courriel = courriel;
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
