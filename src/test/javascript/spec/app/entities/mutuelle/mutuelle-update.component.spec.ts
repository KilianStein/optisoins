/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { MutuelleUpdateComponent } from 'app/entities/mutuelle/mutuelle-update.component';
import { MutuelleService } from 'app/entities/mutuelle/mutuelle.service';
import { Mutuelle } from 'app/shared/model/mutuelle.model';

describe('Component Tests', () => {
    describe('Mutuelle Management Update Component', () => {
        let comp: MutuelleUpdateComponent;
        let fixture: ComponentFixture<MutuelleUpdateComponent>;
        let service: MutuelleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [MutuelleUpdateComponent]
            })
                .overrideTemplate(MutuelleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MutuelleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MutuelleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Mutuelle(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mutuelle = entity;
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
                    const entity = new Mutuelle();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mutuelle = entity;
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
