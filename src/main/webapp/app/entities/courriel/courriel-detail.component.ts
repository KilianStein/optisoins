import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourriel } from 'app/shared/model/courriel.model';

@Component({
    selector: 'jhi-courriel-detail',
    templateUrl: './courriel-detail.component.html'
})
export class CourrielDetailComponent implements OnInit {
    courriel: ICourriel;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ courriel }) => {
            this.courriel = courriel;
        });
    }

    previousState() {
        window.history.back();
    }
}
