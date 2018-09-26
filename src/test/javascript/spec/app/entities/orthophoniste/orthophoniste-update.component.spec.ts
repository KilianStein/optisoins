/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { OrthophonisteUpdateComponent } from 'app/entities/orthophoniste/orthophoniste-update.component';
import { OrthophonisteService } from 'app/entities/orthophoniste/orthophoniste.service';
import { Orthophoniste } from 'app/shared/model/orthophoniste.model';

describe('Component Tests', () => {
    describe('Orthophoniste Management Update Component', () => {
        let comp: OrthophonisteUpdateComponent;
        let fixture: ComponentFixture<OrthophonisteUpdateComponent>;
        let service: OrthophonisteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [OrthophonisteUpdateComponent]
            })
                .overrideTemplate(OrthophonisteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrthophonisteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrthophonisteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Orthophoniste(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orthophoniste = entity;
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
                    const entity = new Orthophoniste();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.orthophoniste = entity;
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
