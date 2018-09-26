import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAmo } from 'app/shared/model/amo.model';

type EntityResponseType = HttpResponse<IAmo>;
type EntityArrayResponseType = HttpResponse<IAmo[]>;

@Injectable({ providedIn: 'root' })
export class AmoService {
    private resourceUrl = SERVER_API_URL + 'api/amos';

    constructor(private http: HttpClient) {}

    create(amo: IAmo): Observable<EntityResponseType> {
        return this.http.post<IAmo>(this.resourceUrl, amo, { observe: 'response' });
    }

    update(amo: IAmo): Observable<EntityResponseType> {
        return this.http.put<IAmo>(this.resourceUrl, amo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAmo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAmo[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
