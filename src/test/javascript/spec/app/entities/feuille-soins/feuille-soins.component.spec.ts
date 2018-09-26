/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { FeuilleSoinsComponent } from 'app/entities/feuille-soins/feuille-soins.component';
import { FeuilleSoinsService } from 'app/entities/feuille-soins/feuille-soins.service';
import { FeuilleSoins } from 'app/shared/model/feuille-soins.model';

describe('Component Tests', () => {
    describe('FeuilleSoins Management Component', () => {
        let comp: FeuilleSoinsComponent;
        let fixture: ComponentFixture<FeuilleSoinsComponent>;
        let service: FeuilleSoinsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [FeuilleSoinsComponent],
                providers: []
            })
                .overrideTemplate(FeuilleSoinsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FeuilleSoinsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeuilleSoinsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FeuilleSoins(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.feuilleSoins[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
