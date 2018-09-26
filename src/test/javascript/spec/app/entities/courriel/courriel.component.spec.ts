/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { CourrielComponent } from 'app/entities/courriel/courriel.component';
import { CourrielService } from 'app/entities/courriel/courriel.service';
import { Courriel } from 'app/shared/model/courriel.model';

describe('Component Tests', () => {
    describe('Courriel Management Component', () => {
        let comp: CourrielComponent;
        let fixture: ComponentFixture<CourrielComponent>;
        let service: CourrielService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CourrielComponent],
                providers: []
            })
                .overrideTemplate(CourrielComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CourrielComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CourrielService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Courriel(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.courriels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
