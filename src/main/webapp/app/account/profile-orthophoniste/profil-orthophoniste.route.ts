import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ProfilOrthophonisteComponent } from './profil-orthophoniste.component';

export const profilOrthophonisteRoute: Route = {
    path: 'profil-orthophoniste',
    component: ProfilOrthophonisteComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'global.menu.account.profil-orthophoniste'
    },
    canActivate: [UserRouteAccessService]
};
