/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { FeuilleSoinsDeleteDialogComponent } from 'app/entities/feuille-soins/feuille-soins-delete-dialog.component';
import { FeuilleSoinsService } from 'app/entities/feuille-soins/feuille-soins.service';

describe('Component Tests', () => {
    describe('FeuilleSoins Management Delete Component', () => {
        let comp: FeuilleSoinsDeleteDialogComponent;
        let fixture: ComponentFixture<FeuilleSoinsDeleteDialogComponent>;
        let service: FeuilleSoinsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [FeuilleSoinsDeleteDialogComponent]
            })
                .overrideTemplate(FeuilleSoinsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FeuilleSoinsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FeuilleSoinsService);
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
