import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FeuilleSoins } from 'app/shared/model/feuille-soins.model';
import { FeuilleSoinsService } from './feuille-soins.service';
import { FeuilleSoinsComponent } from './feuille-soins.component';
import { FeuilleSoinsDetailComponent } from './feuille-soins-detail.component';
import { FeuilleSoinsUpdateComponent } from './feuille-soins-update.component';
import { FeuilleSoinsDeletePopupComponent } from './feuille-soins-delete-dialog.component';
import { IFeuilleSoins } from 'app/shared/model/feuille-soins.model';

@Injectable({ providedIn: 'root' })
export class FeuilleSoinsResolve implements Resolve<IFeuilleSoins> {
    constructor(private service: FeuilleSoinsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((feuilleSoins: HttpResponse<FeuilleSoins>) => feuilleSoins.body));
        }
        return of(new FeuilleSoins());
    }
}

export const feuilleSoinsRoute: Routes = [
    {
        path: 'feuille-soins',
        component: FeuilleSoinsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.feuilleSoins.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feuille-soins/:id/view',
        component: FeuilleSoinsDetailComponent,
        resolve: {
            feuilleSoins: FeuilleSoinsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.feuilleSoins.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feuille-soins/new',
        component: FeuilleSoinsUpdateComponent,
        resolve: {
            feuilleSoins: FeuilleSoinsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.feuilleSoins.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'feuille-soins/:id/edit',
        component: FeuilleSoinsUpdateComponent,
        resolve: {
            feuilleSoins: FeuilleSoinsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.feuilleSoins.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const feuilleSoinsPopupRoute: Routes = [
    {
        path: 'feuille-soins/:id/delete',
        component: FeuilleSoinsDeletePopupComponent,
        resolve: {
            feuilleSoins: FeuilleSoinsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.feuilleSoins.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
