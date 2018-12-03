import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Mutuelle } from 'app/shared/model/mutuelle.model';
import { MutuelleService } from './mutuelle.service';
import { MutuelleComponent } from './mutuelle.component';
import { MutuelleDetailComponent } from './mutuelle-detail.component';
import { MutuelleUpdateComponent } from './mutuelle-update.component';
import { MutuelleDeletePopupComponent } from './mutuelle-delete-dialog.component';
import { IMutuelle } from 'app/shared/model/mutuelle.model';

@Injectable({ providedIn: 'root' })
export class MutuelleResolve implements Resolve<IMutuelle> {
    constructor(private service: MutuelleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Mutuelle> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Mutuelle>) => response.ok),
                map((mutuelle: HttpResponse<Mutuelle>) => mutuelle.body)
            );
        }
        return of(new Mutuelle());
    }
}

export const mutuelleRoute: Routes = [
    {
        path: 'mutuelle',
        component: MutuelleComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.mutuelle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mutuelle/:id/view',
        component: MutuelleDetailComponent,
        resolve: {
            mutuelle: MutuelleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.mutuelle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mutuelle/new',
        component: MutuelleUpdateComponent,
        resolve: {
            mutuelle: MutuelleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.mutuelle.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'mutuelle/:id/edit',
        component: MutuelleUpdateComponent,
        resolve: {
            mutuelle: MutuelleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.mutuelle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mutuellePopupRoute: Routes = [
    {
        path: 'mutuelle/:id/delete',
        component: MutuelleDeletePopupComponent,
        resolve: {
            mutuelle: MutuelleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.mutuelle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
