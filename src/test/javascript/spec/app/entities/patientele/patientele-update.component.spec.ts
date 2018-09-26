/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { PatienteleUpdateComponent } from 'app/entities/patientele/patientele-update.component';
import { PatienteleService } from 'app/entities/patientele/patientele.service';
import { Patientele } from 'app/shared/model/patientele.model';

describe('Component Tests', () => {
    describe('Patientele Management Update Component', () => {
        let comp: PatienteleUpdateComponent;
        let fixture: ComponentFixture<PatienteleUpdateComponent>;
        let service: PatienteleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [PatienteleUpdateComponent]
            })
                .overrideTemplate(PatienteleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PatienteleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatienteleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Patientele(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.patientele = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Patientele();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.patientele = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
