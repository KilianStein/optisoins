/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { CarteAideMedicaleUpdateComponent } from 'app/entities/carte-aide-medicale/carte-aide-medicale-update.component';
import { CarteAideMedicaleService } from 'app/entities/carte-aide-medicale/carte-aide-medicale.service';
import { CarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';

describe('Component Tests', () => {
    describe('CarteAideMedicale Management Update Component', () => {
        let comp: CarteAideMedicaleUpdateComponent;
        let fixture: ComponentFixture<CarteAideMedicaleUpdateComponent>;
        let service: CarteAideMedicaleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CarteAideMedicaleUpdateComponent]
            })
                .overrideTemplate(CarteAideMedicaleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CarteAideMedicaleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarteAideMedicaleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CarteAideMedicale(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.carteAideMedicale = entity;
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
                    const entity = new CarteAideMedicale();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.carteAideMedicale = entity;
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
