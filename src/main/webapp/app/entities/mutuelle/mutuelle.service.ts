import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMutuelle } from 'app/shared/model/mutuelle.model';

type EntityResponseType = HttpResponse<IMutuelle>;
type EntityArrayResponseType = HttpResponse<IMutuelle[]>;

@Injectable({ providedIn: 'root' })
export class MutuelleService {
    private resourceUrl = SERVER_API_URL + 'api/mutuelles';

    constructor(private http: HttpClient) {}

    create(mutuelle: IMutuelle): Observable<EntityResponseType> {
        return this.http.post<IMutuelle>(this.resourceUrl, mutuelle, { observe: 'response' });
    }

    update(mutuelle: IMutuelle): Observable<EntityResponseType> {
        return this.http.put<IMutuelle>(this.resourceUrl, mutuelle, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMutuelle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMutuelle[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
