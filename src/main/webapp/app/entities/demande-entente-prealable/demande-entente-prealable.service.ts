import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';

type EntityResponseType = HttpResponse<IDemandeEntentePrealable>;
type EntityArrayResponseType = HttpResponse<IDemandeEntentePrealable[]>;

@Injectable({ providedIn: 'root' })
export class DemandeEntentePrealableService {
    private resourceUrl = SERVER_API_URL + 'api/demande-entente-prealables';

    constructor(private http: HttpClient) {}

    create(demandeEntentePrealable: IDemandeEntentePrealable): Observable<EntityResponseType> {
        return this.http.post<IDemandeEntentePrealable>(this.resourceUrl, demandeEntentePrealable, { observe: 'response' });
    }

    update(demandeEntentePrealable: IDemandeEntentePrealable): Observable<EntityResponseType> {
        return this.http.put<IDemandeEntentePrealable>(this.resourceUrl, demandeEntentePrealable, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDemandeEntentePrealable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDemandeEntentePrealable[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
