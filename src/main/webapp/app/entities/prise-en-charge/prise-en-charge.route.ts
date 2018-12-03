import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PriseEnCharge } from 'app/shared/model/prise-en-charge.model';
import { PriseEnChargeService } from './prise-en-charge.service';
import { PriseEnChargeComponent } from './prise-en-charge.component';
import { PriseEnChargeDetailComponent } from './prise-en-charge-detail.component';
import { PriseEnChargeUpdateComponent } from './prise-en-charge-update.component';
import { PriseEnChargeDeletePopupComponent } from './prise-en-charge-delete-dialog.component';
import { IPriseEnCharge } from 'app/shared/model/prise-en-charge.model';

@Injectable({ providedIn: 'root' })
export class PriseEnChargeResolve implements Resolve<IPriseEnCharge> {
    constructor(private service: PriseEnChargeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PriseEnCharge> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<PriseEnCharge>) => response.ok),
                map((priseEnCharge: HttpResponse<PriseEnCharge>) => priseEnCharge.body)
            );
        }
        return of(new PriseEnCharge());
    }
}

export const priseEnChargeRoute: Routes = [
    {
        path: 'prise-en-charge',
        component: PriseEnChargeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prise-en-charge/:id/view',
        component: PriseEnChargeDetailComponent,
        resolve: {
            priseEnCharge: PriseEnChargeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prise-en-charge/new',
        component: PriseEnChargeUpdateComponent,
        resolve: {
            priseEnCharge: PriseEnChargeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'prise-en-charge/:id/edit',
        component: PriseEnChargeUpdateComponent,
        resolve: {
            priseEnCharge: PriseEnChargeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const priseEnChargePopupRoute: Routes = [
    {
        path: 'prise-en-charge/:id/delete',
        component: PriseEnChargeDeletePopupComponent,
        resolve: {
            priseEnCharge: PriseEnChargeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.priseEnCharge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
