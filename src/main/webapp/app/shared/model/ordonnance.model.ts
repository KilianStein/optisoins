import { Moment } from 'moment';
import { IDemandeEntentePrealable } from 'app/shared/model//demande-entente-prealable.model';

export interface IOrdonnance {
    id?: number;
    datePrescription?: Moment;
    nombreSeances?: number;
    medecinId?: number;
    demandeEntentePrealables?: IDemandeEntentePrealable[];
    priseEnChargeId?: number;
}

export class Ordonnance implements IOrdonnance {
    constructor(
        public id?: number,
        public datePrescription?: Moment,
        public nombreSeances?: number,
        public medecinId?: number,
        public demandeEntentePrealables?: IDemandeEntentePrealable[],
        public priseEnChargeId?: number
    ) {}
}
