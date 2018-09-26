import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITelephone } from 'app/shared/model/telephone.model';
import { TelephoneService } from './telephone.service';

@Component({
    selector: 'jhi-telephone-delete-dialog',
    templateUrl: './telephone-delete-dialog.component.html'
})
export class TelephoneDeleteDialogComponent {
    telephone: ITelephone;

    constructor(private telephoneService: TelephoneService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.telephoneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'telephoneListModification',
                content: 'Deleted an telephone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-telephone-delete-popup',
    template: ''
})
export class TelephoneDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ telephone }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TelephoneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.telephone = telephone;
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
