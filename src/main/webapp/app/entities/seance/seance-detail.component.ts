import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeance } from 'app/shared/model/seance.model';

@Component({
    selector: 'jhi-seance-detail',
    templateUrl: './seance-detail.component.html'
})
export class SeanceDetailComponent implements OnInit {
    seance: ISeance;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ seance }) => {
            this.seance = seance;
        });
    }

    previousState() {
        window.history.back();
    }
}
