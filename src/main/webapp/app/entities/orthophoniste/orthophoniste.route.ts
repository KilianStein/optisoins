import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Orthophoniste } from 'app/shared/model/orthophoniste.model';
import { OrthophonisteService } from './orthophoniste.service';
import { OrthophonisteComponent } from './orthophoniste.component';
import { OrthophonisteDetailComponent } from './orthophoniste-detail.component';
import { OrthophonisteUpdateComponent } from './orthophoniste-update.component';
import { OrthophonisteDeletePopupComponent } from './orthophoniste-delete-dialog.component';
import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';

@Injectable({ providedIn: 'root' })
export class OrthophonisteResolve implements Resolve<IOrthophoniste> {
    constructor(private service: OrthophonisteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Orthophoniste> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Orthophoniste>) => response.ok),
                map((orthophoniste: HttpResponse<Orthophoniste>) => orthophoniste.body)
            );
        }
        return of(new Orthophoniste());
    }
}

export const orthophonisteRoute: Routes = [
    {
        path: 'orthophoniste',
        component: OrthophonisteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.orthophoniste.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orthophoniste/:id/view',
        component: OrthophonisteDetailComponent,
        resolve: {
            orthophoniste: OrthophonisteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.orthophoniste.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orthophoniste/new',
        component: OrthophonisteUpdateComponent,
        resolve: {
            orthophoniste: OrthophonisteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.orthophoniste.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'orthophoniste/:id/edit',
        component: OrthophonisteUpdateComponent,
        resolve: {
            orthophoniste: OrthophonisteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.orthophoniste.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orthophonistePopupRoute: Routes = [
    {
        path: 'orthophoniste/:id/delete',
        component: OrthophonisteDeletePopupComponent,
        resolve: {
            orthophoniste: OrthophonisteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.orthophoniste.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
