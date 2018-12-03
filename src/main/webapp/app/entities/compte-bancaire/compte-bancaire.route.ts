import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompteBancaire } from 'app/shared/model/compte-bancaire.model';
import { CompteBancaireService } from './compte-bancaire.service';
import { CompteBancaireComponent } from './compte-bancaire.component';
import { CompteBancaireDetailComponent } from './compte-bancaire-detail.component';
import { CompteBancaireUpdateComponent } from './compte-bancaire-update.component';
import { CompteBancaireDeletePopupComponent } from './compte-bancaire-delete-dialog.component';
import { ICompteBancaire } from 'app/shared/model/compte-bancaire.model';

@Injectable({ providedIn: 'root' })
export class CompteBancaireResolve implements Resolve<ICompteBancaire> {
    constructor(private service: CompteBancaireService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CompteBancaire> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CompteBancaire>) => response.ok),
                map((compteBancaire: HttpResponse<CompteBancaire>) => compteBancaire.body)
            );
        }
        return of(new CompteBancaire());
    }
}

export const compteBancaireRoute: Routes = [
    {
        path: 'compte-bancaire',
        component: CompteBancaireComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compte-bancaire/:id/view',
        component: CompteBancaireDetailComponent,
        resolve: {
            compteBancaire: CompteBancaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compte-bancaire/new',
        component: CompteBancaireUpdateComponent,
        resolve: {
            compteBancaire: CompteBancaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compte-bancaire/:id/edit',
        component: CompteBancaireUpdateComponent,
        resolve: {
            compteBancaire: CompteBancaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compteBancairePopupRoute: Routes = [
    {
        path: 'compte-bancaire/:id/delete',
        component: CompteBancaireDeletePopupComponent,
        resolve: {
            compteBancaire: CompteBancaireResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.compteBancaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
