/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { EtatRecapitulatifDetailComponent } from 'app/entities/etat-recapitulatif/etat-recapitulatif-detail.component';
import { EtatRecapitulatif } from 'app/shared/model/etat-recapitulatif.model';

describe('Component Tests', () => {
    describe('EtatRecapitulatif Management Detail Component', () => {
        let comp: EtatRecapitulatifDetailComponent;
        let fixture: ComponentFixture<EtatRecapitulatifDetailComponent>;
        const route = ({ data: of({ etatRecapitulatif: new EtatRecapitulatif(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [EtatRecapitulatifDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EtatRecapitulatifDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EtatRecapitulatifDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.etatRecapitulatif).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
