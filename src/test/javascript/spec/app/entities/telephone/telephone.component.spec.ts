/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { TelephoneComponent } from 'app/entities/telephone/telephone.component';
import { TelephoneService } from 'app/entities/telephone/telephone.service';
import { Telephone } from 'app/shared/model/telephone.model';

describe('Component Tests', () => {
    describe('Telephone Management Component', () => {
        let comp: TelephoneComponent;
        let fixture: ComponentFixture<TelephoneComponent>;
        let service: TelephoneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [TelephoneComponent],
                providers: []
            })
                .overrideTemplate(TelephoneComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TelephoneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TelephoneService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Telephone(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.telephones[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
