import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAmo } from 'app/shared/model/amo.model';

@Component({
    selector: 'jhi-amo-detail',
    templateUrl: './amo-detail.component.html'
})
export class AmoDetailComponent implements OnInit {
    amo: IAmo;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ amo }) => {
            this.amo = amo;
        });
    }

    previousState() {
        window.history.back();
    }
}
