import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPriseEnCharge } from 'app/shared/model/prise-en-charge.model';

type EntityResponseType = HttpResponse<IPriseEnCharge>;
type EntityArrayResponseType = HttpResponse<IPriseEnCharge[]>;

@Injectable({ providedIn: 'root' })
export class PriseEnChargeService {
    private resourceUrl = SERVER_API_URL + 'api/prise-en-charges';

    constructor(private http: HttpClient) {}

    create(priseEnCharge: IPriseEnCharge): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(priseEnCharge);
        return this.http
            .post<IPriseEnCharge>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(priseEnCharge: IPriseEnCharge): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(priseEnCharge);
        return this.http
            .put<IPriseEnCharge>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPriseEnCharge>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPriseEnCharge[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(priseEnCharge: IPriseEnCharge): IPriseEnCharge {
        const copy: IPriseEnCharge = Object.assign({}, priseEnCharge, {
            dateDebut:
                priseEnCharge.dateDebut != null && priseEnCharge.dateDebut.isValid() ? priseEnCharge.dateDebut.format(DATE_FORMAT) : null,
            dateFin: priseEnCharge.dateFin != null && priseEnCharge.dateFin.isValid() ? priseEnCharge.dateFin.format(DATE_FORMAT) : null,
            accident: priseEnCharge.accident != null && priseEnCharge.accident.isValid() ? priseEnCharge.accident.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateDebut = res.body.dateDebut != null ? moment(res.body.dateDebut) : null;
        res.body.dateFin = res.body.dateFin != null ? moment(res.body.dateFin) : null;
        res.body.accident = res.body.accident != null ? moment(res.body.accident) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((priseEnCharge: IPriseEnCharge) => {
            priseEnCharge.dateDebut = priseEnCharge.dateDebut != null ? moment(priseEnCharge.dateDebut) : null;
            priseEnCharge.dateFin = priseEnCharge.dateFin != null ? moment(priseEnCharge.dateFin) : null;
            priseEnCharge.accident = priseEnCharge.accident != null ? moment(priseEnCharge.accident) : null;
        });
        return res;
    }
}
