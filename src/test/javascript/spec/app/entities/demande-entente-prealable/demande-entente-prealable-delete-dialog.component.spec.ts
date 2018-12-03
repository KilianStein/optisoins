/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { DemandeEntentePrealableDeleteDialogComponent } from 'app/entities/demande-entente-prealable/demande-entente-prealable-delete-dialog.component';
import { DemandeEntentePrealableService } from 'app/entities/demande-entente-prealable/demande-entente-prealable.service';

describe('Component Tests', () => {
    describe('DemandeEntentePrealable Management Delete Component', () => {
        let comp: DemandeEntentePrealableDeleteDialogComponent;
        let fixture: ComponentFixture<DemandeEntentePrealableDeleteDialogComponent>;
        let service: DemandeEntentePrealableService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [DemandeEntentePrealableDeleteDialogComponent]
            })
                .overrideTemplate(DemandeEntentePrealableDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DemandeEntentePrealableDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeEntentePrealableService);
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
