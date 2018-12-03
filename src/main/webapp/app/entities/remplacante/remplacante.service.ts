import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRemplacante } from 'app/shared/model/remplacante.model';

type EntityResponseType = HttpResponse<IRemplacante>;
type EntityArrayResponseType = HttpResponse<IRemplacante[]>;

@Injectable({ providedIn: 'root' })
export class RemplacanteService {
    public resourceUrl = SERVER_API_URL + 'api/remplacantes';

    constructor(private http: HttpClient) {}

    create(remplacante: IRemplacante): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(remplacante);
        return this.http
            .post<IRemplacante>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(remplacante: IRemplacante): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(remplacante);
        return this.http
            .put<IRemplacante>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRemplacante>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRemplacante[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(remplacante: IRemplacante): IRemplacante {
        const copy: IRemplacante = Object.assign({}, remplacante, {
            dateDebut: remplacante.dateDebut != null && remplacante.dateDebut.isValid() ? remplacante.dateDebut.format(DATE_FORMAT) : null,
            dateFin: remplacante.dateFin != null && remplacante.dateFin.isValid() ? remplacante.dateFin.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateDebut = res.body.dateDebut != null ? moment(res.body.dateDebut) : null;
            res.body.dateFin = res.body.dateFin != null ? moment(res.body.dateFin) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((remplacante: IRemplacante) => {
                remplacante.dateDebut = remplacante.dateDebut != null ? moment(remplacante.dateDebut) : null;
                remplacante.dateFin = remplacante.dateFin != null ? moment(remplacante.dateFin) : null;
            });
        }
        return res;
    }
}
