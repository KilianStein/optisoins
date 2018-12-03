/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PriseEnChargeService } from 'app/entities/prise-en-charge/prise-en-charge.service';
import { IPriseEnCharge, PriseEnCharge, TypePriseEnCharge } from 'app/shared/model/prise-en-charge.model';

describe('Service Tests', () => {
    describe('PriseEnCharge Service', () => {
        let injector: TestBed;
        let service: PriseEnChargeService;
        let httpMock: HttpTestingController;
        let elemDefault: IPriseEnCharge;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PriseEnChargeService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new PriseEnCharge(0, TypePriseEnCharge.CAFAT_MUTUELLE, currentDate, currentDate, currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebut: currentDate.format(DATE_FORMAT),
                        dateFin: currentDate.format(DATE_FORMAT),
                        accident: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a PriseEnCharge', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateDebut: currentDate.format(DATE_FORMAT),
                        dateFin: currentDate.format(DATE_FORMAT),
                        accident: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateDebut: currentDate,
                        dateFin: currentDate,
                        accident: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new PriseEnCharge(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a PriseEnCharge', async () => {
                const returnedFromService = Object.assign(
                    {
                        type: 'BBBBBB',
                        dateDebut: currentDate.format(DATE_FORMAT),
                        dateFin: currentDate.format(DATE_FORMAT),
                        accident: currentDate.format(DATE_FORMAT),
                        nomTierImplique: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateDebut: currentDate,
                        dateFin: currentDate,
                        accident: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of PriseEnCharge', async () => {
                const returnedFromService = Object.assign(
                    {
                        type: 'BBBBBB',
                        dateDebut: currentDate.format(DATE_FORMAT),
                        dateFin: currentDate.format(DATE_FORMAT),
                        accident: currentDate.format(DATE_FORMAT),
                        nomTierImplique: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateDebut: currentDate,
                        dateFin: currentDate,
                        accident: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a PriseEnCharge', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
