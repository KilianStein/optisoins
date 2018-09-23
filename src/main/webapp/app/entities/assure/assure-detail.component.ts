import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssure } from 'app/shared/model/assure.model';

@Component({
    selector: 'jhi-assure-detail',
    templateUrl: './assure-detail.component.html'
})
export class AssureDetailComponent implements OnInit {
    assure: IAssure;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ assure }) => {
            this.assure = assure;
        });
    }

    previousState() {
        window.history.back();
    }
}
