/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { CarteAideMedicaleComponent } from 'app/entities/carte-aide-medicale/carte-aide-medicale.component';
import { CarteAideMedicaleService } from 'app/entities/carte-aide-medicale/carte-aide-medicale.service';
import { CarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';

describe('Component Tests', () => {
    describe('CarteAideMedicale Management Component', () => {
        let comp: CarteAideMedicaleComponent;
        let fixture: ComponentFixture<CarteAideMedicaleComponent>;
        let service: CarteAideMedicaleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CarteAideMedicaleComponent],
                providers: []
            })
                .overrideTemplate(CarteAideMedicaleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CarteAideMedicaleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarteAideMedicaleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CarteAideMedicale(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.carteAideMedicales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
