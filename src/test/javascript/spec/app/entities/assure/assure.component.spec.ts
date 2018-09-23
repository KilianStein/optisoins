/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { AssureComponent } from 'app/entities/assure/assure.component';
import { AssureService } from 'app/entities/assure/assure.service';
import { Assure } from 'app/shared/model/assure.model';

describe('Component Tests', () => {
    describe('Assure Management Component', () => {
        let comp: AssureComponent;
        let fixture: ComponentFixture<AssureComponent>;
        let service: AssureService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [AssureComponent],
                providers: []
            })
                .overrideTemplate(AssureComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AssureComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssureService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Assure(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.assures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
