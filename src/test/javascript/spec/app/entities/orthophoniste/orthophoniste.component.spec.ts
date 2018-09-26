/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { OrthophonisteComponent } from 'app/entities/orthophoniste/orthophoniste.component';
import { OrthophonisteService } from 'app/entities/orthophoniste/orthophoniste.service';
import { Orthophoniste } from 'app/shared/model/orthophoniste.model';

describe('Component Tests', () => {
    describe('Orthophoniste Management Component', () => {
        let comp: OrthophonisteComponent;
        let fixture: ComponentFixture<OrthophonisteComponent>;
        let service: OrthophonisteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [OrthophonisteComponent],
                providers: []
            })
                .overrideTemplate(OrthophonisteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OrthophonisteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrthophonisteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Orthophoniste(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.orthophonistes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
