import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEtatRecapitulatif } from 'app/shared/model/etat-recapitulatif.model';

@Component({
    selector: 'jhi-etat-recapitulatif-detail',
    templateUrl: './etat-recapitulatif-detail.component.html'
})
export class EtatRecapitulatifDetailComponent implements OnInit {
    etatRecapitulatif: IEtatRecapitulatif;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ etatRecapitulatif }) => {
            this.etatRecapitulatif = etatRecapitulatif;
        });
    }

    previousState() {
        window.history.back();
    }
}
