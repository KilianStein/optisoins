/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { CarteAideMedicaleDeleteDialogComponent } from 'app/entities/carte-aide-medicale/carte-aide-medicale-delete-dialog.component';
import { CarteAideMedicaleService } from 'app/entities/carte-aide-medicale/carte-aide-medicale.service';

describe('Component Tests', () => {
    describe('CarteAideMedicale Management Delete Component', () => {
        let comp: CarteAideMedicaleDeleteDialogComponent;
        let fixture: ComponentFixture<CarteAideMedicaleDeleteDialogComponent>;
        let service: CarteAideMedicaleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [CarteAideMedicaleDeleteDialogComponent]
            })
                .overrideTemplate(CarteAideMedicaleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CarteAideMedicaleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CarteAideMedicaleService);
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
