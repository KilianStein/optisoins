/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { AmoComponent } from 'app/entities/amo/amo.component';
import { AmoService } from 'app/entities/amo/amo.service';
import { Amo } from 'app/shared/model/amo.model';

describe('Component Tests', () => {
    describe('Amo Management Component', () => {
        let comp: AmoComponent;
        let fixture: ComponentFixture<AmoComponent>;
        let service: AmoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [AmoComponent],
                providers: []
            })
                .overrideTemplate(AmoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AmoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Amo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.amos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
