/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { PriseEnChargeComponent } from 'app/entities/prise-en-charge/prise-en-charge.component';
import { PriseEnChargeService } from 'app/entities/prise-en-charge/prise-en-charge.service';
import { PriseEnCharge } from 'app/shared/model/prise-en-charge.model';

describe('Component Tests', () => {
    describe('PriseEnCharge Management Component', () => {
        let comp: PriseEnChargeComponent;
        let fixture: ComponentFixture<PriseEnChargeComponent>;
        let service: PriseEnChargeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [PriseEnChargeComponent],
                providers: []
            })
                .overrideTemplate(PriseEnChargeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PriseEnChargeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriseEnChargeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PriseEnCharge(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.priseEnCharges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
