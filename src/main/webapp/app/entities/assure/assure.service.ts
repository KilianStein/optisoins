import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAssure } from 'app/shared/model/assure.model';

type EntityResponseType = HttpResponse<IAssure>;
type EntityArrayResponseType = HttpResponse<IAssure[]>;

@Injectable({ providedIn: 'root' })
export class AssureService {
    private resourceUrl = SERVER_API_URL + 'api/assures';

    constructor(private http: HttpClient) {}

    create(assure: IAssure): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(assure);
        return this.http
            .post<IAssure>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(assure: IAssure): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(assure);
        return this.http
            .put<IAssure>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAssure>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAssure[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(assure: IAssure): IAssure {
        const copy: IAssure = Object.assign({}, assure, {
            dateNaissance: assure.dateNaissance != null && assure.dateNaissance.isValid() ? assure.dateNaissance.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateNaissance = res.body.dateNaissance != null ? moment(res.body.dateNaissance) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((assure: IAssure) => {
            assure.dateNaissance = assure.dateNaissance != null ? moment(assure.dateNaissance) : null;
        });
        return res;
    }
}
