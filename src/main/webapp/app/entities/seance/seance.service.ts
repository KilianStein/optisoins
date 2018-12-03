import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISeance } from 'app/shared/model/seance.model';

type EntityResponseType = HttpResponse<ISeance>;
type EntityArrayResponseType = HttpResponse<ISeance[]>;

@Injectable({ providedIn: 'root' })
export class SeanceService {
    public resourceUrl = SERVER_API_URL + 'api/seances';

    constructor(private http: HttpClient) {}

    create(seance: ISeance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(seance);
        return this.http
            .post<ISeance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(seance: ISeance): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(seance);
        return this.http
            .put<ISeance>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISeance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISeance[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(seance: ISeance): ISeance {
        const copy: ISeance = Object.assign({}, seance, {
            dateDebut: seance.dateDebut != null && seance.dateDebut.isValid() ? seance.dateDebut.toJSON() : null,
            dateFin: seance.dateFin != null && seance.dateFin.isValid() ? seance.dateFin.toJSON() : null
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
            res.body.forEach((seance: ISeance) => {
                seance.dateDebut = seance.dateDebut != null ? moment(seance.dateDebut) : null;
                seance.dateFin = seance.dateFin != null ? moment(seance.dateFin) : null;
            });
        }
        return res;
    }
}
