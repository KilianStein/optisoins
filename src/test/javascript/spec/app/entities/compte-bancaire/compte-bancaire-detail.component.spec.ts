/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OptisoinsTestModule } from '../../../test.module';
import { CompteBancaireDetailComponent } from 'app/entities/compte-bancaire/compte-bancaire-detail.component';
import { CompteBancaire } from 'app/shared/model/compte-bancaire.model';

describe('Component Tests', () => {
    describe('CompteBancaire Management Detail Component', () => {
        let comp: CompteBancaireDetailComponent;
        let fixture: ComponentFixture<CompteBancaireDetailComponent>;
        const route = ({ data: of({ compteBancaire: new CompteBancaire(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CompteBancaireDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CompteBancaireDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompteBancaireDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.compteBancaire).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
