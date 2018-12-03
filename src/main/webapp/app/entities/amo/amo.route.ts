import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Amo } from 'app/shared/model/amo.model';
import { AmoService } from './amo.service';
import { AmoComponent } from './amo.component';
import { AmoDetailComponent } from './amo-detail.component';
import { AmoUpdateComponent } from './amo-update.component';
import { AmoDeletePopupComponent } from './amo-delete-dialog.component';
import { IAmo } from 'app/shared/model/amo.model';

@Injectable({ providedIn: 'root' })
export class AmoResolve implements Resolve<IAmo> {
    constructor(private service: AmoService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Amo> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Amo>) => response.ok),
                map((amo: HttpResponse<Amo>) => amo.body)
            );
        }
        return of(new Amo());
    }
}

export const amoRoute: Routes = [
    {
        path: 'amo',
        component: AmoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.amo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amo/:id/view',
        component: AmoDetailComponent,
        resolve: {
            amo: AmoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.amo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amo/new',
        component: AmoUpdateComponent,
        resolve: {
            amo: AmoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.amo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'amo/:id/edit',
        component: AmoUpdateComponent,
        resolve: {
            amo: AmoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.amo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amoPopupRoute: Routes = [
    {
        path: 'amo/:id/delete',
        component: AmoDeletePopupComponent,
        resolve: {
            amo: AmoResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.amo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
