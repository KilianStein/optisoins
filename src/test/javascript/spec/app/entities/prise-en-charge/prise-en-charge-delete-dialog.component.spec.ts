/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { PriseEnChargeDeleteDialogComponent } from 'app/entities/prise-en-charge/prise-en-charge-delete-dialog.component';
import { PriseEnChargeService } from 'app/entities/prise-en-charge/prise-en-charge.service';

describe('Component Tests', () => {
    describe('PriseEnCharge Management Delete Component', () => {
        let comp: PriseEnChargeDeleteDialogComponent;
        let fixture: ComponentFixture<PriseEnChargeDeleteDialogComponent>;
        let service: PriseEnChargeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [PriseEnChargeDeleteDialogComponent]
            })
                .overrideTemplate(PriseEnChargeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PriseEnChargeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PriseEnChargeService);
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
