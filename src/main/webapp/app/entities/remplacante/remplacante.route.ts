import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Remplacante } from 'app/shared/model/remplacante.model';
import { RemplacanteService } from './remplacante.service';
import { RemplacanteComponent } from './remplacante.component';
import { RemplacanteDetailComponent } from './remplacante-detail.component';
import { RemplacanteUpdateComponent } from './remplacante-update.component';
import { RemplacanteDeletePopupComponent } from './remplacante-delete-dialog.component';
import { IRemplacante } from 'app/shared/model/remplacante.model';

@Injectable({ providedIn: 'root' })
export class RemplacanteResolve implements Resolve<IRemplacante> {
    constructor(private service: RemplacanteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((remplacante: HttpResponse<Remplacante>) => remplacante.body));
        }
        return of(new Remplacante());
    }
}

export const remplacanteRoute: Routes = [
    {
        path: 'remplacante',
        component: RemplacanteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.remplacante.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'remplacante/:id/view',
        component: RemplacanteDetailComponent,
        resolve: {
            remplacante: RemplacanteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.remplacante.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'remplacante/new',
        component: RemplacanteUpdateComponent,
        resolve: {
            remplacante: RemplacanteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.remplacante.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'remplacante/:id/edit',
        component: RemplacanteUpdateComponent,
        resolve: {
            remplacante: RemplacanteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.remplacante.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const remplacantePopupRoute: Routes = [
    {
        path: 'remplacante/:id/delete',
        component: RemplacanteDeletePopupComponent,
        resolve: {
            remplacante: RemplacanteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.remplacante.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
