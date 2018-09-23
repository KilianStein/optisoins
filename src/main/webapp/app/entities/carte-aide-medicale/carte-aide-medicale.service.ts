import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';

type EntityResponseType = HttpResponse<ICarteAideMedicale>;
type EntityArrayResponseType = HttpResponse<ICarteAideMedicale[]>;

@Injectable({ providedIn: 'root' })
export class CarteAideMedicaleService {
    private resourceUrl = SERVER_API_URL + 'api/carte-aide-medicales';

    constructor(private http: HttpClient) {}

    create(carteAideMedicale: ICarteAideMedicale): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(carteAideMedicale);
        return this.http
            .post<ICarteAideMedicale>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(carteAideMedicale: ICarteAideMedicale): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(carteAideMedicale);
        return this.http
            .put<ICarteAideMedicale>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICarteAideMedicale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICarteAideMedicale[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(carteAideMedicale: ICarteAideMedicale): ICarteAideMedicale {
        const copy: ICarteAideMedicale = Object.assign({}, carteAideMedicale, {
            dateDebutValidite:
                carteAideMedicale.dateDebutValidite != null && carteAideMedicale.dateDebutValidite.isValid()
                    ? carteAideMedicale.dateDebutValidite.format(DATE_FORMAT)
                    : null,
            dateFinValidite:
                carteAideMedicale.dateFinValidite != null && carteAideMedicale.dateFinValidite.isValid()
                    ? carteAideMedicale.dateFinValidite.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateDebutValidite = res.body.dateDebutValidite != null ? moment(res.body.dateDebutValidite) : null;
        res.body.dateFinValidite = res.body.dateFinValidite != null ? moment(res.body.dateFinValidite) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((carteAideMedicale: ICarteAideMedicale) => {
            carteAideMedicale.dateDebutValidite =
                carteAideMedicale.dateDebutValidite != null ? moment(carteAideMedicale.dateDebutValidite) : null;
            carteAideMedicale.dateFinValidite =
                carteAideMedicale.dateFinValidite != null ? moment(carteAideMedicale.dateFinValidite) : null;
        });
        return res;
    }
}
