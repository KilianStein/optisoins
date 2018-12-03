import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFeuilleSoins } from 'app/shared/model/feuille-soins.model';

type EntityResponseType = HttpResponse<IFeuilleSoins>;
type EntityArrayResponseType = HttpResponse<IFeuilleSoins[]>;

@Injectable({ providedIn: 'root' })
export class FeuilleSoinsService {
    public resourceUrl = SERVER_API_URL + 'api/feuille-soins';

    constructor(private http: HttpClient) {}

    create(feuilleSoins: IFeuilleSoins): Observable<EntityResponseType> {
        return this.http.post<IFeuilleSoins>(this.resourceUrl, feuilleSoins, { observe: 'response' });
    }

    update(feuilleSoins: IFeuilleSoins): Observable<EntityResponseType> {
        return this.http.put<IFeuilleSoins>(this.resourceUrl, feuilleSoins, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFeuilleSoins>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFeuilleSoins[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
