/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { PriseEnChargeDetailComponent } from 'app/entities/prise-en-charge/prise-en-charge-detail.component';
import { PriseEnCharge } from 'app/shared/model/prise-en-charge.model';

describe('Component Tests', () => {
    describe('PriseEnCharge Management Detail Component', () => {
        let comp: PriseEnChargeDetailComponent;
        let fixture: ComponentFixture<PriseEnChargeDetailComponent>;
        const route = ({ data: of({ priseEnCharge: new PriseEnCharge(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [PriseEnChargeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PriseEnChargeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PriseEnChargeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.priseEnCharge).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
