/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { MutuelleComponent } from 'app/entities/mutuelle/mutuelle.component';
import { MutuelleService } from 'app/entities/mutuelle/mutuelle.service';
import { Mutuelle } from 'app/shared/model/mutuelle.model';

describe('Component Tests', () => {
    describe('Mutuelle Management Component', () => {
        let comp: MutuelleComponent;
        let fixture: ComponentFixture<MutuelleComponent>;
        let service: MutuelleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [MutuelleComponent],
                providers: []
            })
                .overrideTemplate(MutuelleComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MutuelleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MutuelleService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Mutuelle(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.mutuelles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
