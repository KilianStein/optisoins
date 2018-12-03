/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { RemplacanteUpdateComponent } from 'app/entities/remplacante/remplacante-update.component';
import { RemplacanteService } from 'app/entities/remplacante/remplacante.service';
import { Remplacante } from 'app/shared/model/remplacante.model';

describe('Component Tests', () => {
    describe('Remplacante Management Update Component', () => {
        let comp: RemplacanteUpdateComponent;
        let fixture: ComponentFixture<RemplacanteUpdateComponent>;
        let service: RemplacanteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [RemplacanteUpdateComponent]
            })
                .overrideTemplate(RemplacanteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RemplacanteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemplacanteService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Remplacante(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.remplacante = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Remplacante();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.remplacante = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
