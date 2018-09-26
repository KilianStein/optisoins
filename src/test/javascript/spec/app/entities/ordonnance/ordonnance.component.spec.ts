/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { OrdonnanceComponent } from 'app/entities/ordonnance/ordonnance.component';
import { OrdonnanceService } from 'app/entities/ordonnance/ordonnance.service';
import { Ordonnance } from 'app/shared/model/ordonnance.model';

describe('Component Tests', () => {
    describe('Ordonnance Management Component', () => {
        let comp: OrdonnanceComponent;
        let fixture: ComponentFixture<OrdonnanceComponent>;
        let service: OrdonnanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [OrdonnanceComponent],
                providers: []
            })
                .overrideTemplate(OrdonnanceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrdonnanceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdonnanceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Ordonnance(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.ordonnances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
