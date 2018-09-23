import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEtatRecapitulatif } from 'app/shared/model/etat-recapitulatif.model';

type EntityResponseType = HttpResponse<IEtatRecapitulatif>;
type EntityArrayResponseType = HttpResponse<IEtatRecapitulatif[]>;

@Injectable({ providedIn: 'root' })
export class EtatRecapitulatifService {
    private resourceUrl = SERVER_API_URL + 'api/etat-recapitulatifs';

    constructor(private http: HttpClient) {}

    create(etatRecapitulatif: IEtatRecapitulatif): Observable<EntityResponseType> {
        return this.http.post<IEtatRecapitulatif>(this.resourceUrl, etatRecapitulatif, { observe: 'response' });
    }

    update(etatRecapitulatif: IEtatRecapitulatif): Observable<EntityResponseType> {
        return this.http.put<IEtatRecapitulatif>(this.resourceUrl, etatRecapitulatif, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEtatRecapitulatif>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEtatRecapitulatif[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
