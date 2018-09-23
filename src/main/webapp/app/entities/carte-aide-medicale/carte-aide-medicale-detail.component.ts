import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';

@Component({
    selector: 'jhi-carte-aide-medicale-detail',
    templateUrl: './carte-aide-medicale-detail.component.html'
})
export class CarteAideMedicaleDetailComponent implements OnInit {
    carteAideMedicale: ICarteAideMedicale;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ carteAideMedicale }) => {
            this.carteAideMedicale = carteAideMedicale;
        });
    }

    previousState() {
        window.history.back();
    }
}
