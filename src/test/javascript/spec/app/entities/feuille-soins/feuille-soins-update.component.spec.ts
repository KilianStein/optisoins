/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { FeuilleSoinsUpdateComponent } from 'app/entities/feuille-soins/feuille-soins-update.component';
import { FeuilleSoinsService } from 'app/entities/feuille-soins/feuille-soins.service';
import { FeuilleSoins } from 'app/shared/model/feuille-soins.model';

describe('Component Tests', () => {
    describe('FeuilleSoins Management Update Component', () => {
        let comp: FeuilleSoinsUpdateComponent;
        let fixture: ComponentFixture<FeuilleSoinsUpdateComponent>;
        let service: FeuilleSoinsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [FeuilleSoinsUpdateComponent]
            })
                .overrideTemplate(FeuilleSoinsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FeuilleSoinsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeuilleSoinsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FeuilleSoins(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.feuilleSoins = entity;
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
                    const entity = new FeuilleSoins();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.feuilleSoins = entity;
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
