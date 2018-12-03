import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Patientele } from 'app/shared/model/patientele.model';
import { PatienteleService } from './patientele.service';
import { PatienteleComponent } from './patientele.component';
import { PatienteleDetailComponent } from './patientele-detail.component';
import { PatienteleUpdateComponent } from './patientele-update.component';
import { PatienteleDeletePopupComponent } from './patientele-delete-dialog.component';
import { IPatientele } from 'app/shared/model/patientele.model';

@Injectable({ providedIn: 'root' })
export class PatienteleResolve implements Resolve<IPatientele> {
    constructor(private service: PatienteleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Patientele> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Patientele>) => response.ok),
                map((patientele: HttpResponse<Patientele>) => patientele.body)
            );
        }
        return of(new Patientele());
    }
}

export const patienteleRoute: Routes = [
    {
        path: 'patientele',
        component: PatienteleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.patientele.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patientele/:id/view',
        component: PatienteleDetailComponent,
        resolve: {
            patientele: PatienteleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.patientele.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patientele/new',
        component: PatienteleUpdateComponent,
        resolve: {
            patientele: PatienteleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.patientele.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'patientele/:id/edit',
        component: PatienteleUpdateComponent,
        resolve: {
            patientele: PatienteleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.patientele.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const patientelePopupRoute: Routes = [
    {
        path: 'patientele/:id/delete',
        component: PatienteleDeletePopupComponent,
        resolve: {
            patientele: PatienteleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.patientele.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
