/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { OrdonnanceDeleteDialogComponent } from 'app/entities/ordonnance/ordonnance-delete-dialog.component';
import { OrdonnanceService } from 'app/entities/ordonnance/ordonnance.service';

describe('Component Tests', () => {
    describe('Ordonnance Management Delete Component', () => {
        let comp: OrdonnanceDeleteDialogComponent;
        let fixture: ComponentFixture<OrdonnanceDeleteDialogComponent>;
        let service: OrdonnanceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [OrdonnanceDeleteDialogComponent]
            })
                .overrideTemplate(OrdonnanceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrdonnanceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdonnanceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
