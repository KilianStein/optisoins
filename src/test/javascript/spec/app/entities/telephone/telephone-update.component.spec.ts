/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { TelephoneUpdateComponent } from 'app/entities/telephone/telephone-update.component';
import { TelephoneService } from 'app/entities/telephone/telephone.service';
import { Telephone } from 'app/shared/model/telephone.model';

describe('Component Tests', () => {
    describe('Telephone Management Update Component', () => {
        let comp: TelephoneUpdateComponent;
        let fixture: ComponentFixture<TelephoneUpdateComponent>;
        let service: TelephoneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [TelephoneUpdateComponent]
            })
                .overrideTemplate(TelephoneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TelephoneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TelephoneService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Telephone(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.telephone = entity;
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
                    const entity = new Telephone();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.telephone = entity;
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
