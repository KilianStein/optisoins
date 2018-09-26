import { Moment } from 'moment';
import { IOrdonnance } from 'app/shared/model//ordonnance.model';
import { ISeance } from 'app/shared/model//seance.model';
import { IFeuilleSoins } from 'app/shared/model//feuille-soins.model';

export const enum TypePriseEnCharge {
    CAFAT_MUTUELLE = 'CAFAT_MUTUELLE',
    LONGUE_MALADIE = 'LONGUE_MALADIE',
    AIDE_MEDICALE_SUD = 'AIDE_MEDICALE_SUD',
    AIDE_MEDICALE_NORD = 'AIDE_MEDICALE_NORD',
    AIDE_MEDICALE_ILES = 'AIDE_MEDICALE_ILES'
}

export interface IPriseEnCharge {
    id?: number;
    type?: TypePriseEnCharge;
    dateDebut?: Moment;
    dateFin?: Moment;
    accident?: Moment;
    nomTierImplique?: string;
    patientId?: number;
    ordonnances?: IOrdonnance[];
    seances?: ISeance[];
    feuillesSoins?: IFeuilleSoins[];
}

export class PriseEnCharge implements IPriseEnCharge {
    constructor(
        public id?: number,
        public type?: TypePriseEnCharge,
        public dateDebut?: Moment,
        public dateFin?: Moment,
        public accident?: Moment,
        public nomTierImplique?: string,
        public patientId?: number,
        public ordonnances?: IOrdonnance[],
        public seances?: ISeance[],
        public feuillesSoins?: IFeuilleSoins[]
    ) {}
}
