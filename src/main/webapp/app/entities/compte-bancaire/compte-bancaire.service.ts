import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompteBancaire } from 'app/shared/model/compte-bancaire.model';

type EntityResponseType = HttpResponse<ICompteBancaire>;
type EntityArrayResponseType = HttpResponse<ICompteBancaire[]>;

@Injectable({ providedIn: 'root' })
export class CompteBancaireService {
    private resourceUrl = SERVER_API_URL + 'api/compte-bancaires';

    constructor(private http: HttpClient) {}

    create(compteBancaire: ICompteBancaire): Observable<EntityResponseType> {
        return this.http.post<ICompteBancaire>(this.resourceUrl, compteBancaire, { observe: 'response' });
    }

    update(compteBancaire: ICompteBancaire): Observable<EntityResponseType> {
        return this.http.put<ICompteBancaire>(this.resourceUrl, compteBancaire, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICompteBancaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICompteBancaire[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
