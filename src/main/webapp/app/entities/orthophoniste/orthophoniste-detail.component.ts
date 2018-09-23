import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';

@Component({
    selector: 'jhi-orthophoniste-detail',
    templateUrl: './orthophoniste-detail.component.html'
})
export class OrthophonisteDetailComponent implements OnInit {
    orthophoniste: IOrthophoniste;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orthophoniste }) => {
            this.orthophoniste = orthophoniste;
        });
    }

    previousState() {
        window.history.back();
    }
}
