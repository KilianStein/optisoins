import { Component, OnInit } from '@angular/core';

import { Principal } from 'app/core';
import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';
import { OrthophonisteService } from 'app/entities/orthophoniste';
import { log } from 'util';

@Component({
    selector: 'jhi-settings',
    templateUrl: './profil-orthophoniste.component.html'
})
export class ProfilOrthophonisteComponent implements OnInit {
    error: string;
    success: string;
    settingsAccount: any;
    orthophoniste: IOrthophoniste;

    constructor(private principal: Principal, private orthophonisteService: OrthophonisteService) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.orthophonisteService.find(account.orthophonisteId).subscribe(httpReponse => {
                this.orthophoniste = httpReponse.body;
                log(this.orthophoniste.nom);
            });
        });
    }

    save() {
        this.orthophonisteService.update(this.orthophoniste).subscribe(
            () => {
                this.error = null;
                this.success = 'OK';
            },
            () => {
                this.success = null;
                this.error = 'ERROR';
            }
        );
    }
}
