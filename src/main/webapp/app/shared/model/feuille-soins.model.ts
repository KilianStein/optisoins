import { ISeance } from 'app/shared/model//seance.model';

export interface IFeuilleSoins {
    id?: number;
    priseEnChargeId?: number;
    actes?: ISeance[];
    etatRecapitulatifId?: number;
}

export class FeuilleSoins implements IFeuilleSoins {
    constructor(public id?: number, public priseEnChargeId?: number, public actes?: ISeance[], public etatRecapitulatifId?: number) {}
}
