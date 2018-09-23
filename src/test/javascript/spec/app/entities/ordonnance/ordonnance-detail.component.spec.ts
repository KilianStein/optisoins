/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { OrdonnanceDetailComponent } from 'app/entities/ordonnance/ordonnance-detail.component';
import { Ordonnance } from 'app/shared/model/ordonnance.model';

describe('Component Tests', () => {
    describe('Ordonnance Management Detail Component', () => {
        let comp: OrdonnanceDetailComponent;
        let fixture: ComponentFixture<OrdonnanceDetailComponent>;
        const route = ({ data: of({ ordonnance: new Ordonnance(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [OrdonnanceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrdonnanceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrdonnanceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ordonnance).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
