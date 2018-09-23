import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Courriel } from 'app/shared/model/courriel.model';
import { CourrielService } from './courriel.service';
import { CourrielComponent } from './courriel.component';
import { CourrielDetailComponent } from './courriel-detail.component';
import { CourrielUpdateComponent } from './courriel-update.component';
import { CourrielDeletePopupComponent } from './courriel-delete-dialog.component';
import { ICourriel } from 'app/shared/model/courriel.model';

@Injectable({ providedIn: 'root' })
export class CourrielResolve implements Resolve<ICourriel> {
    constructor(private service: CourrielService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((courriel: HttpResponse<Courriel>) => courriel.body));
        }
        return of(new Courriel());
    }
}

export const courrielRoute: Routes = [
    {
        path: 'courriel',
        component: CourrielComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.courriel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courriel/:id/view',
        component: CourrielDetailComponent,
        resolve: {
            courriel: CourrielResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.courriel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courriel/new',
        component: CourrielUpdateComponent,
        resolve: {
            courriel: CourrielResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.courriel.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'courriel/:id/edit',
        component: CourrielUpdateComponent,
        resolve: {
            courriel: CourrielResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.courriel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const courrielPopupRoute: Routes = [
    {
        path: 'courriel/:id/delete',
        component: CourrielDeletePopupComponent,
        resolve: {
            courriel: CourrielResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.courriel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
