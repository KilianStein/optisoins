/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { DemandeEntentePrealableComponent } from 'app/entities/demande-entente-prealable/demande-entente-prealable.component';
import { DemandeEntentePrealableService } from 'app/entities/demande-entente-prealable/demande-entente-prealable.service';
import { DemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';

describe('Component Tests', () => {
    describe('DemandeEntentePrealable Management Component', () => {
        let comp: DemandeEntentePrealableComponent;
        let fixture: ComponentFixture<DemandeEntentePrealableComponent>;
        let service: DemandeEntentePrealableService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [DemandeEntentePrealableComponent],
                providers: []
            })
                .overrideTemplate(DemandeEntentePrealableComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DemandeEntentePrealableComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeEntentePrealableService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DemandeEntentePrealable(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.demandeEntentePrealables[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
