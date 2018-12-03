import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMutuelle } from 'app/shared/model/mutuelle.model';

@Component({
    selector: 'jhi-mutuelle-detail',
    templateUrl: './mutuelle-detail.component.html'
})
export class MutuelleDetailComponent implements OnInit {
    mutuelle: IMutuelle;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mutuelle }) => {
            this.mutuelle = mutuelle;
        });
    }

    previousState() {
        window.history.back();
    }
}
