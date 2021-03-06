/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { SeanceDetailComponent } from 'app/entities/seance/seance-detail.component';
import { Seance } from 'app/shared/model/seance.model';

describe('Component Tests', () => {
    describe('Seance Management Detail Component', () => {
        let comp: SeanceDetailComponent;
        let fixture: ComponentFixture<SeanceDetailComponent>;
        const route = ({ data: of({ seance: new Seance(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [SeanceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SeanceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SeanceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.seance).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
