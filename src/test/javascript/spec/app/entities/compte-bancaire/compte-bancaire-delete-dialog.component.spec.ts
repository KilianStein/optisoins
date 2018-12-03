/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { CompteBancaireDeleteDialogComponent } from 'app/entities/compte-bancaire/compte-bancaire-delete-dialog.component';
import { CompteBancaireService } from 'app/entities/compte-bancaire/compte-bancaire.service';

describe('Component Tests', () => {
    describe('CompteBancaire Management Delete Component', () => {
        let comp: CompteBancaireDeleteDialogComponent;
        let fixture: ComponentFixture<CompteBancaireDeleteDialogComponent>;
        let service: CompteBancaireService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CompteBancaireDeleteDialogComponent]
            })
                .overrideTemplate(CompteBancaireDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompteBancaireDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompteBancaireService);
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
