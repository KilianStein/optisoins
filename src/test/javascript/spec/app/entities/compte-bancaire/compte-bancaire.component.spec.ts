/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { CompteBancaireComponent } from 'app/entities/compte-bancaire/compte-bancaire.component';
import { CompteBancaireService } from 'app/entities/compte-bancaire/compte-bancaire.service';
import { CompteBancaire } from 'app/shared/model/compte-bancaire.model';

describe('Component Tests', () => {
    describe('CompteBancaire Management Component', () => {
        let comp: CompteBancaireComponent;
        let fixture: ComponentFixture<CompteBancaireComponent>;
        let service: CompteBancaireService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CompteBancaireComponent],
                providers: []
            })
                .overrideTemplate(CompteBancaireComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompteBancaireComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteBancaireService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new CompteBancaire(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.compteBancaires[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
