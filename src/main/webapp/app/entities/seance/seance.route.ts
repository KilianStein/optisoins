import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Seance } from 'app/shared/model/seance.model';
import { SeanceService } from './seance.service';
import { SeanceComponent } from './seance.component';
import { SeanceDetailComponent } from './seance-detail.component';
import { SeanceUpdateComponent } from './seance-update.component';
import { SeanceDeletePopupComponent } from './seance-delete-dialog.component';
import { ISeance } from 'app/shared/model/seance.model';

@Injectable({ providedIn: 'root' })
export class SeanceResolve implements Resolve<ISeance> {
    constructor(private service: SeanceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((seance: HttpResponse<Seance>) => seance.body));
        }
        return of(new Seance());
    }
}

export const seanceRoute: Routes = [
    {
        path: 'seance',
        component: SeanceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.seance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seance/:id/view',
        component: SeanceDetailComponent,
        resolve: {
            seance: SeanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.seance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seance/new',
        component: SeanceUpdateComponent,
        resolve: {
            seance: SeanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.seance.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'seance/:id/edit',
        component: SeanceUpdateComponent,
        resolve: {
            seance: SeanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.seance.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seancePopupRoute: Routes = [
    {
        path: 'seance/:id/delete',
        component: SeanceDeletePopupComponent,
        resolve: {
            seance: SeanceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'optisoinsApp.seance.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
