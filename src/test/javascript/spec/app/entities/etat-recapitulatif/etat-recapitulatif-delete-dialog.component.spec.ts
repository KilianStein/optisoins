/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { EtatRecapitulatifDeleteDialogComponent } from 'app/entities/etat-recapitulatif/etat-recapitulatif-delete-dialog.component';
import { EtatRecapitulatifService } from 'app/entities/etat-recapitulatif/etat-recapitulatif.service';

describe('Component Tests', () => {
    describe('EtatRecapitulatif Management Delete Component', () => {
        let comp: EtatRecapitulatifDeleteDialogComponent;
        let fixture: ComponentFixture<EtatRecapitulatifDeleteDialogComponent>;
        let service: EtatRecapitulatifService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [EtatRecapitulatifDeleteDialogComponent]
            })
                .overrideTemplate(EtatRecapitulatifDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EtatRecapitulatifDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtatRecapitulatifService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
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
            ));
        });
    });
});
