import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrdonnance } from 'app/shared/model/ordonnance.model';

type EntityResponseType = HttpResponse<IOrdonnance>;
type EntityArrayResponseType = HttpResponse<IOrdonnance[]>;

@Injectable({ providedIn: 'root' })
export class OrdonnanceService {
    public resourceUrl = SERVER_API_URL + 'api/ordonnances';

    constructor(private http: HttpClient) {}

    create(ordonnance: IOrdonnance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ordonnance);
        return this.http
            .post<IOrdonnance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(ordonnance: IOrdonnance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(ordonnance);
        return this.http
            .put<IOrdonnance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IOrdonnance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IOrdonnance[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(ordonnance: IOrdonnance): IOrdonnance {
        const copy: IOrdonnance = Object.assign({}, ordonnance, {
            datePrescription:
                ordonnance.datePrescription != null && ordonnance.datePrescription.isValid()
                    ? ordonnance.datePrescription.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.datePrescription = res.body.datePrescription != null ? moment(res.body.datePrescription) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((ordonnance: IOrdonnance) => {
                ordonnance.datePrescription = ordonnance.datePrescription != null ? moment(ordonnance.datePrescription) : null;
            });
        }
        return res;
    }
}
