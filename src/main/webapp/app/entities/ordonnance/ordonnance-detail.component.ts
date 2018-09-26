import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrdonnance } from 'app/shared/model/ordonnance.model';

@Component({
    selector: 'jhi-ordonnance-detail',
    templateUrl: './ordonnance-detail.component.html'
})
export class OrdonnanceDetailComponent implements OnInit {
    ordonnance: IOrdonnance;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ordonnance }) => {
            this.ordonnance = ordonnance;
        });
    }

    previousState() {
        window.history.back();
    }
}
