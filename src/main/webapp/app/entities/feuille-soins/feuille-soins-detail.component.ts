import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFeuilleSoins } from 'app/shared/model/feuille-soins.model';

@Component({
    selector: 'jhi-feuille-soins-detail',
    templateUrl: './feuille-soins-detail.component.html'
})
export class FeuilleSoinsDetailComponent implements OnInit {
    feuilleSoins: IFeuilleSoins;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ feuilleSoins }) => {
            this.feuilleSoins = feuilleSoins;
        });
    }

    previousState() {
        window.history.back();
    }
}
