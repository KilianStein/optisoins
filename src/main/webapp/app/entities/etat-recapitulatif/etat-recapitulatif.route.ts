import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EtatRecapitulatif } from 'app/shared/model/etat-recapitulatif.model';
import { EtatRecapitulatifService } from './etat-recapitulatif.service';
import { EtatRecapitulatifComponent } from './etat-recapitulatif.component';
import { EtatRecapitulatifDetailComponent } from './etat-recapitulatif-detail.component';
import { EtatRecapitulatifUpdateComponent } from './etat-recapitulatif-update.component';
import { EtatRecapitulatifDeletePopupComponent } from './etat-recapitulatif-delete-dialog.component';
import { IEtatRecapitulatif } from 'app/shared/model/etat-recapitulatif.model';

@Injectable({ providedIn: 'root' })
export class EtatRecapitulatifResolve implements Resolve<IEtatRecapitulatif> {
    constructor(private service: EtatRecapitulatifService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<EtatRecapitulatif> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<EtatRecapitulatif>) => response.ok),
                map((etatRecapitulatif: HttpResponse<EtatRecapitulatif>) => etatRecapitulatif.body)
            );
        }
        return of(new EtatRecapitulatif());
    }
}

export const etatRecapitulatifRoute: Routes = [
    {
        path: 'etat-recapitulatif',
        component: EtatRecapitulatifComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.etatRecapitulatif.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'etat-recapitulatif/:id/view',
        component: EtatRecapitulatifDetailComponent,
        resolve: {
            etatRecapitulatif: EtatRecapitulatifResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.etatRecapitulatif.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'etat-recapitulatif/new',
        component: EtatRecapitulatifUpdateComponent,
        resolve: {
            etatRecapitulatif: EtatRecapitulatifResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.etatRecapitulatif.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'etat-recapitulatif/:id/edit',
        component: EtatRecapitulatifUpdateComponent,
        resolve: {
            etatRecapitulatif: EtatRecapitulatifResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.etatRecapitulatif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const etatRecapitulatifPopupRoute: Routes = [
    {
        path: 'etat-recapitulatif/:id/delete',
        component: EtatRecapitulatifDeletePopupComponent,
        resolve: {
            etatRecapitulatif: EtatRecapitulatifResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.etatRecapitulatif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
