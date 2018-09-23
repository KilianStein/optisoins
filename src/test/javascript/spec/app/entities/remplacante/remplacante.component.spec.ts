/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { RemplacanteComponent } from 'app/entities/remplacante/remplacante.component';
import { RemplacanteService } from 'app/entities/remplacante/remplacante.service';
import { Remplacante } from 'app/shared/model/remplacante.model';

describe('Component Tests', () => {
    describe('Remplacante Management Component', () => {
        let comp: RemplacanteComponent;
        let fixture: ComponentFixture<RemplacanteComponent>;
        let service: RemplacanteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [RemplacanteComponent],
                providers: []
            })
                .overrideTemplate(RemplacanteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RemplacanteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemplacanteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Remplacante(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.remplacantes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
