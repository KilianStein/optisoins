import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';
import { DemandeEntentePrealableService } from './demande-entente-prealable.service';
import { DemandeEntentePrealableComponent } from './demande-entente-prealable.component';
import { DemandeEntentePrealableDetailComponent } from './demande-entente-prealable-detail.component';
import { DemandeEntentePrealableUpdateComponent } from './demande-entente-prealable-update.component';
import { DemandeEntentePrealableDeletePopupComponent } from './demande-entente-prealable-delete-dialog.component';
import { IDemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';

@Injectable({ providedIn: 'root' })
export class DemandeEntentePrealableResolve implements Resolve<IDemandeEntentePrealable> {
    constructor(private service: DemandeEntentePrealableService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((demandeEntentePrealable: HttpResponse<DemandeEntentePrealable>) => demandeEntentePrealable.body));
        }
        return of(new DemandeEntentePrealable());
    }
}

export const demandeEntentePrealableRoute: Routes = [
    {
        path: 'demande-entente-prealable',
        component: DemandeEntentePrealableComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.demandeEntentePrealable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demande-entente-prealable/:id/view',
        component: DemandeEntentePrealableDetailComponent,
        resolve: {
            demandeEntentePrealable: DemandeEntentePrealableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.demandeEntentePrealable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demande-entente-prealable/new',
        component: DemandeEntentePrealableUpdateComponent,
        resolve: {
            demandeEntentePrealable: DemandeEntentePrealableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.demandeEntentePrealable.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'demande-entente-prealable/:id/edit',
        component: DemandeEntentePrealableUpdateComponent,
        resolve: {
            demandeEntentePrealable: DemandeEntentePrealableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.demandeEntentePrealable.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const demandeEntentePrealablePopupRoute: Routes = [
    {
        path: 'demande-entente-prealable/:id/delete',
        component: DemandeEntentePrealableDeletePopupComponent,
        resolve: {
            demandeEntentePrealable: DemandeEntentePrealableResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.demandeEntentePrealable.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
