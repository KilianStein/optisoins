import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ordonnance } from 'app/shared/model/ordonnance.model';
import { OrdonnanceService } from './ordonnance.service';
import { OrdonnanceComponent } from './ordonnance.component';
import { OrdonnanceDetailComponent } from './ordonnance-detail.component';
import { OrdonnanceUpdateComponent } from './ordonnance-update.component';
import { OrdonnanceDeletePopupComponent } from './ordonnance-delete-dialog.component';
import { IOrdonnance } from 'app/shared/model/ordonnance.model';

@Injectable({ providedIn: 'root' })
export class OrdonnanceResolve implements Resolve<IOrdonnance> {
    constructor(private service: OrdonnanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Ordonnance> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Ordonnance>) => response.ok),
                map((ordonnance: HttpResponse<Ordonnance>) => ordonnance.body)
            );
        }
        return of(new Ordonnance());
    }
}

export const ordonnanceRoute: Routes = [
    {
        path: 'ordonnance',
        component: OrdonnanceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.ordonnance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ordonnance/:id/view',
        component: OrdonnanceDetailComponent,
        resolve: {
            ordonnance: OrdonnanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.ordonnance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ordonnance/new',
        component: OrdonnanceUpdateComponent,
        resolve: {
            ordonnance: OrdonnanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.ordonnance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ordonnance/:id/edit',
        component: OrdonnanceUpdateComponent,
        resolve: {
            ordonnance: OrdonnanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.ordonnance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ordonnancePopupRoute: Routes = [
    {
        path: 'ordonnance/:id/delete',
        component: OrdonnanceDeletePopupComponent,
        resolve: {
            ordonnance: OrdonnanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.ordonnance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
