/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RemplacanteService } from 'app/entities/remplacante/remplacante.service';
import { IRemplacante, Remplacante } from 'app/shared/model/remplacante.model';

describe('Service Tests', () => {
    describe('Remplacante Service', () => {
        let injector: TestBed;
        let service: RemplacanteService;
        let httpMock: HttpTestingController;
        let elemDefault: IRemplacante;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RemplacanteService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Remplacante(0, currentDate, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebut: currentDate.format(DATE_FORMAT),
                        dateFin: currentDate.format(DATE_FORMAT)
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

            it('should create a Remplacante', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateDebut: currentDate.format(DATE_FORMAT),
                        dateFin: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateDebut: currentDate,
                        dateFin: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Remplacante(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Remplacante', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebut: currentDate.format(DATE_FORMAT),
                        dateFin: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateDebut: currentDate,
                        dateFin: currentDate
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

            it('should return a list of Remplacante', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebut: currentDate.format(DATE_FORMAT),
                        dateFin: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateDebut: currentDate,
                        dateFin: currentDate
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

            it('should delete a Remplacante', async () => {
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
