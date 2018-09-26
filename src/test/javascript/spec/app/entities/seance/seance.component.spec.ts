/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OptisoinsTestModule } from '../../../test.module';
import { SeanceComponent } from 'app/entities/seance/seance.component';
import { SeanceService } from 'app/entities/seance/seance.service';
import { Seance } from 'app/shared/model/seance.model';

describe('Component Tests', () => {
    describe('Seance Management Component', () => {
        let comp: SeanceComponent;
        let fixture: ComponentFixture<SeanceComponent>;
        let service: SeanceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [SeanceComponent],
                providers: []
            })
                .overrideTemplate(SeanceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SeanceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeanceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Seance(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.seances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
