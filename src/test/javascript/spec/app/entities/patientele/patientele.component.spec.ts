/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { PatienteleComponent } from 'app/entities/patientele/patientele.component';
import { PatienteleService } from 'app/entities/patientele/patientele.service';
import { Patientele } from 'app/shared/model/patientele.model';

describe('Component Tests', () => {
    describe('Patientele Management Component', () => {
        let comp: PatienteleComponent;
        let fixture: ComponentFixture<PatienteleComponent>;
        let service: PatienteleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [PatienteleComponent],
                providers: []
            })
                .overrideTemplate(PatienteleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PatienteleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatienteleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Patientele(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.patienteles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
