/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { CourrielDetailComponent } from 'app/entities/courriel/courriel-detail.component';
import { Courriel } from 'app/shared/model/courriel.model';

describe('Component Tests', () => {
    describe('Courriel Management Detail Component', () => {
        let comp: CourrielDetailComponent;
        let fixture: ComponentFixture<CourrielDetailComponent>;
        const route = ({ data: of({ courriel: new Courriel(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CourrielDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CourrielDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CourrielDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.courriel).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
