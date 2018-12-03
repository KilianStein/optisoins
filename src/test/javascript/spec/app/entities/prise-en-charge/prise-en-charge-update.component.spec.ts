/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { PriseEnChargeUpdateComponent } from 'app/entities/prise-en-charge/prise-en-charge-update.component';
import { PriseEnChargeService } from 'app/entities/prise-en-charge/prise-en-charge.service';
import { PriseEnCharge } from 'app/shared/model/prise-en-charge.model';

describe('Component Tests', () => {
    describe('PriseEnCharge Management Update Component', () => {
        let comp: PriseEnChargeUpdateComponent;
        let fixture: ComponentFixture<PriseEnChargeUpdateComponent>;
        let service: PriseEnChargeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [PriseEnChargeUpdateComponent]
            })
                .overrideTemplate(PriseEnChargeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PriseEnChargeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriseEnChargeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new PriseEnCharge(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.priseEnCharge = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new PriseEnCharge();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.priseEnCharge = entity;
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
