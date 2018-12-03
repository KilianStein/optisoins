/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { CarteAideMedicaleDetailComponent } from 'app/entities/carte-aide-medicale/carte-aide-medicale-detail.component';
import { CarteAideMedicale } from 'app/shared/model/carte-aide-medicale.model';

describe('Component Tests', () => {
    describe('CarteAideMedicale Management Detail Component', () => {
        let comp: CarteAideMedicaleDetailComponent;
        let fixture: ComponentFixture<CarteAideMedicaleDetailComponent>;
        const route = ({ data: of({ carteAideMedicale: new CarteAideMedicale(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CarteAideMedicaleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CarteAideMedicaleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CarteAideMedicaleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.carteAideMedicale).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
