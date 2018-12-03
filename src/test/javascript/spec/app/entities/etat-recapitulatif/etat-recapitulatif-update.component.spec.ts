/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { EtatRecapitulatifUpdateComponent } from 'app/entities/etat-recapitulatif/etat-recapitulatif-update.component';
import { EtatRecapitulatifService } from 'app/entities/etat-recapitulatif/etat-recapitulatif.service';
import { EtatRecapitulatif } from 'app/shared/model/etat-recapitulatif.model';

describe('Component Tests', () => {
    describe('EtatRecapitulatif Management Update Component', () => {
        let comp: EtatRecapitulatifUpdateComponent;
        let fixture: ComponentFixture<EtatRecapitulatifUpdateComponent>;
        let service: EtatRecapitulatifService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [EtatRecapitulatifUpdateComponent]
            })
                .overrideTemplate(EtatRecapitulatifUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EtatRecapitulatifUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtatRecapitulatifService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new EtatRecapitulatif(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.etatRecapitulatif = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new EtatRecapitulatif();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.etatRecapitulatif = entity;
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
