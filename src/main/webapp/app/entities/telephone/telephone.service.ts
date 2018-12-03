import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITelephone } from 'app/shared/model/telephone.model';

type EntityResponseType = HttpResponse<ITelephone>;
type EntityArrayResponseType = HttpResponse<ITelephone[]>;

@Injectable({ providedIn: 'root' })
export class TelephoneService {
    public resourceUrl = SERVER_API_URL + 'api/telephones';

    constructor(private http: HttpClient) {}

    create(telephone: ITelephone): Observable<EntityResponseType> {
        return this.http.post<ITelephone>(this.resourceUrl, telephone, { observe: 'response' });
    }

    update(telephone: ITelephone): Observable<EntityResponseType> {
        return this.http.put<ITelephone>(this.resourceUrl, telephone, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITelephone>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITelephone[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
