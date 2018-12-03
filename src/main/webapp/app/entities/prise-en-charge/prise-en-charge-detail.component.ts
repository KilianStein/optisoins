import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPriseEnCharge } from 'app/shared/model/prise-en-charge.model';

@Component({
    selector: 'jhi-prise-en-charge-detail',
    templateUrl: './prise-en-charge-detail.component.html'
})
export class PriseEnChargeDetailComponent implements OnInit {
    priseEnCharge: IPriseEnCharge;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ priseEnCharge }) => {
            this.priseEnCharge = priseEnCharge;
        });
    }

    previousState() {
        window.history.back();
    }
}
