/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { PatienteleDetailComponent } from 'app/entities/patientele/patientele-detail.component';
import { Patientele } from 'app/shared/model/patientele.model';

describe('Component Tests', () => {
    describe('Patientele Management Detail Component', () => {
        let comp: PatienteleDetailComponent;
        let fixture: ComponentFixture<PatienteleDetailComponent>;
        const route = ({ data: of({ patientele: new Patientele(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [PatienteleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PatienteleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PatienteleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.patientele).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
