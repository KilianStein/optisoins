import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICourriel } from 'app/shared/model/courriel.model';

type EntityResponseType = HttpResponse<ICourriel>;
type EntityArrayResponseType = HttpResponse<ICourriel[]>;

@Injectable({ providedIn: 'root' })
export class CourrielService {
    public resourceUrl = SERVER_API_URL + 'api/courriels';

    constructor(private http: HttpClient) {}

    create(courriel: ICourriel): Observable<EntityResponseType> {
        return this.http.post<ICourriel>(this.resourceUrl, courriel, { observe: 'response' });
    }

    update(courriel: ICourriel): Observable<EntityResponseType> {
        return this.http.put<ICourriel>(this.resourceUrl, courriel, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICourriel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICourriel[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
