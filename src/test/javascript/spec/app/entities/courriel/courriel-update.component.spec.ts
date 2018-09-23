/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { CourrielUpdateComponent } from 'app/entities/courriel/courriel-update.component';
import { CourrielService } from 'app/entities/courriel/courriel.service';
import { Courriel } from 'app/shared/model/courriel.model';

describe('Component Tests', () => {
    describe('Courriel Management Update Component', () => {
        let comp: CourrielUpdateComponent;
        let fixture: ComponentFixture<CourrielUpdateComponent>;
        let service: CourrielService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CourrielUpdateComponent]
            })
                .overrideTemplate(CourrielUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourrielUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourrielService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Courriel(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courriel = entity;
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
                    const entity = new Courriel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.courriel = entity;
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
