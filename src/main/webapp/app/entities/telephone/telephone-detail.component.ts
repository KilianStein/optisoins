import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITelephone } from 'app/shared/model/telephone.model';

@Component({
    selector: 'jhi-telephone-detail',
    templateUrl: './telephone-detail.component.html'
})
export class TelephoneDetailComponent implements OnInit {
    telephone: ITelephone;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ telephone }) => {
            this.telephone = telephone;
        });
    }

    previousState() {
        window.history.back();
    }
}
