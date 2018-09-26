/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { OrthophonisteDetailComponent } from 'app/entities/orthophoniste/orthophoniste-detail.component';
import { Orthophoniste } from 'app/shared/model/orthophoniste.model';

describe('Component Tests', () => {
    describe('Orthophoniste Management Detail Component', () => {
        let comp: OrthophonisteDetailComponent;
        let fixture: ComponentFixture<OrthophonisteDetailComponent>;
        const route = ({ data: of({ orthophoniste: new Orthophoniste(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [OrthophonisteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrthophonisteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrthophonisteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orthophoniste).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
