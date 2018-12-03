import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';
import { CarteAideMedicaleService } from './carte-aide-medicale.service';

@Component({
    selector: 'jhi-carte-aide-medicale-delete-dialog',
    templateUrl: './carte-aide-medicale-delete-dialog.component.html'
})
export class CarteAideMedicaleDeleteDialogComponent {
    carteAideMedicale: ICarteAideMedicale;

    constructor(
        private carteAideMedicaleService: CarteAideMedicaleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.carteAideMedicaleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'carteAideMedicaleListModification',
                content: 'Deleted an carteAideMedicale'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-carte-aide-medicale-delete-popup',
    template: ''
})
export class CarteAideMedicaleDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ carteAideMedicale }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CarteAideMedicaleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.carteAideMedicale = carteAideMedicale;
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
