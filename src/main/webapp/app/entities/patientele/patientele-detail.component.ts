import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPatientele } from 'app/shared/model/patientele.model';

@Component({
    selector: 'jhi-patientele-detail',
    templateUrl: './patientele-detail.component.html'
})
export class PatienteleDetailComponent implements OnInit {
    patientele: IPatientele;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ patientele }) => {
            this.patientele = patientele;
        });
    }

    previousState() {
        window.history.back();
    }
}
