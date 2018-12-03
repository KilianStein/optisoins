import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompteBancaire } from 'app/shared/model/compte-bancaire.model';

@Component({
    selector: 'jhi-compte-bancaire-detail',
    templateUrl: './compte-bancaire-detail.component.html'
})
export class CompteBancaireDetailComponent implements OnInit {
    compteBancaire: ICompteBancaire;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compteBancaire }) => {
            this.compteBancaire = compteBancaire;
        });
    }

    previousState() {
        window.history.back();
    }
}
