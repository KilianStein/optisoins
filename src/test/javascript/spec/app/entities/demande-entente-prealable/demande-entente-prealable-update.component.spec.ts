/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { DemandeEntentePrealableUpdateComponent } from 'app/entities/demande-entente-prealable/demande-entente-prealable-update.component';
import { DemandeEntentePrealableService } from 'app/entities/demande-entente-prealable/demande-entente-prealable.service';
import { DemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';

describe('Component Tests', () => {
    describe('DemandeEntentePrealable Management Update Component', () => {
        let comp: DemandeEntentePrealableUpdateComponent;
        let fixture: ComponentFixture<DemandeEntentePrealableUpdateComponent>;
        let service: DemandeEntentePrealableService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [DemandeEntentePrealableUpdateComponent]
            })
                .overrideTemplate(DemandeEntentePrealableUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DemandeEntentePrealableUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeEntentePrealableService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DemandeEntentePrealable(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.demandeEntentePrealable = entity;
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
                    const entity = new DemandeEntentePrealable();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.demandeEntentePrealable = entity;
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
