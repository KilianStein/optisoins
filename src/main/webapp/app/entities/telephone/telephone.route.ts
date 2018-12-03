import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Telephone } from 'app/shared/model/telephone.model';
import { TelephoneService } from './telephone.service';
import { TelephoneComponent } from './telephone.component';
import { TelephoneDetailComponent } from './telephone-detail.component';
import { TelephoneUpdateComponent } from './telephone-update.component';
import { TelephoneDeletePopupComponent } from './telephone-delete-dialog.component';
import { ITelephone } from 'app/shared/model/telephone.model';

@Injectable({ providedIn: 'root' })
export class TelephoneResolve implements Resolve<ITelephone> {
    constructor(private service: TelephoneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Telephone> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Telephone>) => response.ok),
                map((telephone: HttpResponse<Telephone>) => telephone.body)
            );
        }
        return of(new Telephone());
    }
}

export const telephoneRoute: Routes = [
    {
        path: 'telephone',
        component: TelephoneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.telephone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'telephone/:id/view',
        component: TelephoneDetailComponent,
        resolve: {
            telephone: TelephoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.telephone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'telephone/new',
        component: TelephoneUpdateComponent,
        resolve: {
            telephone: TelephoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.telephone.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'telephone/:id/edit',
        component: TelephoneUpdateComponent,
        resolve: {
            telephone: TelephoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.telephone.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const telephonePopupRoute: Routes = [
    {
        path: 'telephone/:id/delete',
        component: TelephoneDeletePopupComponent,
        resolve: {
            telephone: TelephoneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.telephone.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
