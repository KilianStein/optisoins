import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPatientele } from 'app/shared/model/patientele.model';

type EntityResponseType = HttpResponse<IPatientele>;
type EntityArrayResponseType = HttpResponse<IPatientele[]>;

@Injectable({ providedIn: 'root' })
export class PatienteleService {
    public resourceUrl = SERVER_API_URL + 'api/patienteles';

    constructor(private http: HttpClient) {}

    create(patientele: IPatientele): Observable<EntityResponseType> {
        return this.http.post<IPatientele>(this.resourceUrl, patientele, { observe: 'response' });
    }

    update(patientele: IPatientele): Observable<EntityResponseType> {
        return this.http.put<IPatientele>(this.resourceUrl, patientele, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPatientele>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPatientele[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
