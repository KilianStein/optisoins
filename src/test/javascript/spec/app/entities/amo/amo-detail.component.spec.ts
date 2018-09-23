/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { AmoDetailComponent } from 'app/entities/amo/amo-detail.component';
import { Amo } from 'app/shared/model/amo.model';

describe('Component Tests', () => {
    describe('Amo Management Detail Component', () => {
        let comp: AmoDetailComponent;
        let fixture: ComponentFixture<AmoDetailComponent>;
        const route = ({ data: of({ amo: new Amo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [AmoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AmoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AmoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.amo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
