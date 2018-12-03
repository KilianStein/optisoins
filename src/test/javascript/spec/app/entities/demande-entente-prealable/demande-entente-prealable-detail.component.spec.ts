/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { DemandeEntentePrealableDetailComponent } from 'app/entities/demande-entente-prealable/demande-entente-prealable-detail.component';
import { DemandeEntentePrealable } from 'app/shared/model/demande-entente-prealable.model';

describe('Component Tests', () => {
    describe('DemandeEntentePrealable Management Detail Component', () => {
        let comp: DemandeEntentePrealableDetailComponent;
        let fixture: ComponentFixture<DemandeEntentePrealableDetailComponent>;
        const route = ({ data: of({ demandeEntentePrealable: new DemandeEntentePrealable(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [DemandeEntentePrealableDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DemandeEntentePrealableDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DemandeEntentePrealableDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.demandeEntentePrealable).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
