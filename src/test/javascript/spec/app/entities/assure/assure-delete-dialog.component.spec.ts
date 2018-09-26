/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { AssureDeleteDialogComponent } from 'app/entities/assure/assure-delete-dialog.component';
import { AssureService } from 'app/entities/assure/assure.service';

describe('Component Tests', () => {
    describe('Assure Management Delete Component', () => {
        let comp: AssureDeleteDialogComponent;
        let fixture: ComponentFixture<AssureDeleteDialogComponent>;
        let service: AssureService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [AssureDeleteDialogComponent]
            })
                .overrideTemplate(AssureDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AssureDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssureService);
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
