/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { FeuilleSoinsDetailComponent } from 'app/entities/feuille-soins/feuille-soins-detail.component';
import { FeuilleSoins } from 'app/shared/model/feuille-soins.model';

describe('Component Tests', () => {
    describe('FeuilleSoins Management Detail Component', () => {
        let comp: FeuilleSoinsDetailComponent;
        let fixture: ComponentFixture<FeuilleSoinsDetailComponent>;
        const route = ({ data: of({ feuilleSoins: new FeuilleSoins(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [FeuilleSoinsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FeuilleSoinsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FeuilleSoinsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.feuilleSoins).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
