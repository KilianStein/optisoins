/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { TelephoneDetailComponent } from 'app/entities/telephone/telephone-detail.component';
import { Telephone } from 'app/shared/model/telephone.model';

describe('Component Tests', () => {
    describe('Telephone Management Detail Component', () => {
        let comp: TelephoneDetailComponent;
        let fixture: ComponentFixture<TelephoneDetailComponent>;
        const route = ({ data: of({ telephone: new Telephone(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [TelephoneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TelephoneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TelephoneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.telephone).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
