/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { CompteBancaireUpdateComponent } from 'app/entities/compte-bancaire/compte-bancaire-update.component';
import { CompteBancaireService } from 'app/entities/compte-bancaire/compte-bancaire.service';
import { CompteBancaire } from 'app/shared/model/compte-bancaire.model';

describe('Component Tests', () => {
    describe('CompteBancaire Management Update Component', () => {
        let comp: CompteBancaireUpdateComponent;
        let fixture: ComponentFixture<CompteBancaireUpdateComponent>;
        let service: CompteBancaireService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CompteBancaireUpdateComponent]
            })
                .overrideTemplate(CompteBancaireUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompteBancaireUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteBancaireService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CompteBancaire(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.compteBancaire = entity;
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
                    const entity = new CompteBancaire();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.compteBancaire = entity;
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
