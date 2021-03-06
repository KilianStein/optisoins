import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Medecin } from 'app/shared/model/medecin.model';
import { MedecinService } from './medecin.service';
import { MedecinComponent } from './medecin.component';
import { MedecinDetailComponent } from './medecin-detail.component';
import { MedecinUpdateComponent } from './medecin-update.component';
import { MedecinDeletePopupComponent } from './medecin-delete-dialog.component';
import { IMedecin } from 'app/shared/model/medecin.model';

@Injectable({ providedIn: 'root' })
export class MedecinResolve implements Resolve<IMedecin> {
    constructor(private service: MedecinService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((medecin: HttpResponse<Medecin>) => medecin.body));
        }
        return of(new Medecin());
    }
}

export const medecinRoute: Routes = [
    {
        path: 'medecin',
        component: MedecinComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.medecin.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medecin/:id/view',
        component: MedecinDetailComponent,
        resolve: {
            medecin: MedecinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.medecin.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medecin/new',
        component: MedecinUpdateComponent,
        resolve: {
            medecin: MedecinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.medecin.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medecin/:id/edit',
        component: MedecinUpdateComponent,
        resolve: {
            medecin: MedecinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.medecin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medecinPopupRoute: Routes = [
    {
        path: 'medecin/:id/delete',
        component: MedecinDeletePopupComponent,
        resolve: {
            medecin: MedecinResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.medecin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
