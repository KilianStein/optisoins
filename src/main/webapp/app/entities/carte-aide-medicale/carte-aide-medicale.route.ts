import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';
import { CarteAideMedicaleService } from './carte-aide-medicale.service';
import { CarteAideMedicaleComponent } from './carte-aide-medicale.component';
import { CarteAideMedicaleDetailComponent } from './carte-aide-medicale-detail.component';
import { CarteAideMedicaleUpdateComponent } from './carte-aide-medicale-update.component';
import { CarteAideMedicaleDeletePopupComponent } from './carte-aide-medicale-delete-dialog.component';
import { ICarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';

@Injectable({ providedIn: 'root' })
export class CarteAideMedicaleResolve implements Resolve<ICarteAideMedicale> {
    constructor(private service: CarteAideMedicaleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((carteAideMedicale: HttpResponse<CarteAideMedicale>) => carteAideMedicale.body));
        }
        return of(new CarteAideMedicale());
    }
}

export const carteAideMedicaleRoute: Routes = [
    {
        path: 'carte-aide-medicale',
        component: CarteAideMedicaleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.carteAideMedicale.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'carte-aide-medicale/:id/view',
        component: CarteAideMedicaleDetailComponent,
        resolve: {
            carteAideMedicale: CarteAideMedicaleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.carteAideMedicale.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'carte-aide-medicale/new',
        component: CarteAideMedicaleUpdateComponent,
        resolve: {
            carteAideMedicale: CarteAideMedicaleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.carteAideMedicale.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'carte-aide-medicale/:id/edit',
        component: CarteAideMedicaleUpdateComponent,
        resolve: {
            carteAideMedicale: CarteAideMedicaleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.carteAideMedicale.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const carteAideMedicalePopupRoute: Routes = [
    {
        path: 'carte-aide-medicale/:id/delete',
        component: CarteAideMedicaleDeletePopupComponent,
        resolve: {
            carteAideMedicale: CarteAideMedicaleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.carteAideMedicale.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
