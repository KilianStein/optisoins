/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { MutuelleDetailComponent } from 'app/entities/mutuelle/mutuelle-detail.component';
import { Mutuelle } from 'app/shared/model/mutuelle.model';

describe('Component Tests', () => {
    describe('Mutuelle Management Detail Component', () => {
        let comp: MutuelleDetailComponent;
        let fixture: ComponentFixture<MutuelleDetailComponent>;
        const route = ({ data: of({ mutuelle: new Mutuelle(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [MutuelleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MutuelleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MutuelleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mutuelle).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
