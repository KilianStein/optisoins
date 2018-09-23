import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRemplacante } from 'app/shared/model/remplacante.model';

@Component({
    selector: 'jhi-remplacante-detail',
    templateUrl: './remplacante-detail.component.html'
})
export class RemplacanteDetailComponent implements OnInit {
    remplacante: IRemplacante;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ remplacante }) => {
            this.remplacante = remplacante;
        });
    }

    previousState() {
        window.history.back();
    }
}
