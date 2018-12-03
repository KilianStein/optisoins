import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrthophoniste } from 'app/shared/model/orthophoniste.model';

type EntityResponseType = HttpResponse<IOrthophoniste>;
type EntityArrayResponseType = HttpResponse<IOrthophoniste[]>;

@Injectable({ providedIn: 'root' })
export class OrthophonisteService {
    public resourceUrl = SERVER_API_URL + 'api/orthophonistes';

    constructor(private http: HttpClient) {}

    create(orthophoniste: IOrthophoniste): Observable<EntityResponseType> {
        return this.http.post<IOrthophoniste>(this.resourceUrl, orthophoniste, { observe: 'response' });
    }

    update(orthophoniste: IOrthophoniste): Observable<EntityResponseType> {
        return this.http.put<IOrthophoniste>(this.resourceUrl, orthophoniste, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOrthophoniste>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrthophoniste[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
