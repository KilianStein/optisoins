/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { RemplacanteDeleteDialogComponent } from 'app/entities/remplacante/remplacante-delete-dialog.component';
import { RemplacanteService } from 'app/entities/remplacante/remplacante.service';

describe('Component Tests', () => {
    describe('Remplacante Management Delete Component', () => {
        let comp: RemplacanteDeleteDialogComponent;
        let fixture: ComponentFixture<RemplacanteDeleteDialogComponent>;
        let service: RemplacanteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [RemplacanteDeleteDialogComponent]
            })
                .overrideTemplate(RemplacanteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RemplacanteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemplacanteService);
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
