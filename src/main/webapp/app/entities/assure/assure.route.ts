import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Assure } from 'app/shared/model/assure.model';
import { AssureService } from './assure.service';
import { AssureComponent } from './assure.component';
import { AssureDetailComponent } from './assure-detail.component';
import { AssureUpdateComponent } from './assure-update.component';
import { AssureDeletePopupComponent } from './assure-delete-dialog.component';
import { IAssure } from 'app/shared/model/assure.model';

@Injectable({ providedIn: 'root' })
export class AssureResolve implements Resolve<IAssure> {
    constructor(private service: AssureService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((assure: HttpResponse<Assure>) => assure.body));
        }
        return of(new Assure());
    }
}

export const assureRoute: Routes = [
    {
        path: 'assure',
        component: AssureComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.assure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'assure/:id/view',
        component: AssureDetailComponent,
        resolve: {
            assure: AssureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.assure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'assure/new',
        component: AssureUpdateComponent,
        resolve: {
            assure: AssureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.assure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'assure/:id/edit',
        component: AssureUpdateComponent,
        resolve: {
            assure: AssureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.assure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assurePopupRoute: Routes = [
    {
        path: 'assure/:id/delete',
        component: AssureDeletePopupComponent,
        resolve: {
            assure: AssureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.assure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
