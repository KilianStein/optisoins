/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { AmoUpdateComponent } from 'app/entities/amo/amo-update.component';
import { AmoService } from 'app/entities/amo/amo.service';
import { Amo } from 'app/shared/model/amo.model';

describe('Component Tests', () => {
    describe('Amo Management Update Component', () => {
        let comp: AmoUpdateComponent;
        let fixture: ComponentFixture<AmoUpdateComponent>;
        let service: AmoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [AmoUpdateComponent]
            })
                .overrideTemplate(AmoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AmoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Amo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.amo = entity;
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
                    const entity = new Amo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.amo = entity;
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
