/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { OptisoinsTestModule } from '../../../test.module';
import { OrthophonisteDeleteDialogComponent } from 'app/entities/orthophoniste/orthophoniste-delete-dialog.component';
import { OrthophonisteService } from 'app/entities/orthophoniste/orthophoniste.service';

describe('Component Tests', () => {
    describe('Orthophoniste Management Delete Component', () => {
        let comp: OrthophonisteDeleteDialogComponent;
        let fixture: ComponentFixture<OrthophonisteDeleteDialogComponent>;
        let service: OrthophonisteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [OptisoinsTestModule],
                declarations: [OrthophonisteDeleteDialogComponent]
            })
                .overrideTemplate(OrthophonisteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrthophonisteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrthophonisteService);
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
