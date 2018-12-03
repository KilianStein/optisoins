import { IFeuilleSoins } from 'app/shared/model//feuille-soins.model';

export const enum TypePriseEnCharge {
    CAFAT_MUTUELLE = 'CAFAT_MUTUELLE',
    LONGUE_MALADIE = 'LONGUE_MALADIE',
    AIDE_MEDICALE_SUD = 'AIDE_MEDICALE_SUD',
    AIDE_MEDICALE_NORD = 'AIDE_MEDICALE_NORD',
    AIDE_MEDICALE_ILES = 'AIDE_MEDICALE_ILES'
}

export interface IEtatRecapitulatif {
    id?: number;
    type?: TypePriseEnCharge;
    orthophonisteId?: number;
    feuilleSoins?: IFeuilleSoins[];
}

export class EtatRecapitulatif implements IEtatRecapitulatif {
    constructor(
        public id?: number,
        public type?: TypePriseEnCharge,
        public orthophonisteId?: number,
        public feuilleSoins?: IFeuilleSoins[]
    ) {}
}
