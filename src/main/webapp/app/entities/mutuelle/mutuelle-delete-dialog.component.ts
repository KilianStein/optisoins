import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMutuelle } from 'app/shared/model/mutuelle.model';
import { MutuelleService } from './mutuelle.service';

@Component({
    selector: 'jhi-mutuelle-delete-dialog',
    templateUrl: './mutuelle-delete-dialog.component.html'
})
export class MutuelleDeleteDialogComponent {
    mutuelle: IMutuelle;

    constructor(private mutuelleService: MutuelleService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mutuelleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mutuelleListModification',
                content: 'Deleted an mutuelle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mutuelle-delete-popup',
    template: ''
})
export class MutuelleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mutuelle }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MutuelleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.mutuelle = mutuelle;
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
