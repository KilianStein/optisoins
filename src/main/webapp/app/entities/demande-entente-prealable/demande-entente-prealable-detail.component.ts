import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';

@Component({
    selector: 'jhi-demande-entente-prealable-detail',
    templateUrl: './demande-entente-prealable-detail.component.html'
})
export class DemandeEntentePrealableDetailComponent implements OnInit {
    demandeEntentePrealable: IDemandeEntentePrealable;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ demandeEntentePrealable }) => {
            this.demandeEntentePrealable = demandeEntentePrealable;
        });
    }

    previousState() {
        window.history.back();
    }
}
