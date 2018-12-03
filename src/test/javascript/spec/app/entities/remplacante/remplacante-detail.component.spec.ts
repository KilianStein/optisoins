/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { RemplacanteDetailComponent } from 'app/entities/remplacante/remplacante-detail.component';
import { Remplacante } from 'app/shared/model/remplacante.model';

describe('Component Tests', () => {
    describe('Remplacante Management Detail Component', () => {
        let comp: RemplacanteDetailComponent;
        let fixture: ComponentFixture<RemplacanteDetailComponent>;
        const route = ({ data: of({ remplacante: new Remplacante(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [RemplacanteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RemplacanteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RemplacanteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.remplacante).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
