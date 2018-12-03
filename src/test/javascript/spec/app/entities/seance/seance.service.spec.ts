/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SeanceService } from 'app/entities/seance/seance.service';
import { ISeance, Seance, EtatSeance } from 'app/shared/model/seance.model';

describe('Service Tests', () => {
    describe('Seance Service', () => {
        let injector: TestBed;
        let service: SeanceService;
        let httpMock: HttpTestingController;
        let elemDefault: ISeance;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(SeanceService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Seance(0, currentDate, currentDate, 'AAAAAAA', false, false, false, EtatSeance.PLANIFIE, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebut: currentDate.format(DATE_TIME_FORMAT),
                        dateFin: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Seance', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateDebut: currentDate.format(DATE_TIME_FORMAT),
                        dateFin: currentDate.format(DATE_TIME_FORMAT)
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
                    .create(new Seance(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Seance', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebut: currentDate.format(DATE_TIME_FORMAT),
                        dateFin: currentDate.format(DATE_TIME_FORMAT),
                        origine: 'BBBBBB',
                        domicile: true,
                        ticketModerateur: true,
                        bilan: true,
                        etat: 'BBBBBB',
                        commentaire: 'BBBBBB'
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

            it('should return a list of Seance', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateDebut: currentDate.format(DATE_TIME_FORMAT),
                        dateFin: currentDate.format(DATE_TIME_FORMAT),
                        origine: 'BBBBBB',
                        domicile: true,
                        ticketModerateur: true,
                        bilan: true,
                        etat: 'BBBBBB',
                        commentaire: 'BBBBBB'
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

            it('should delete a Seance', async () => {
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
