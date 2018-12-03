import { Moment } from 'moment';

export const enum EtatSeance {
    PLANIFIE = 'PLANIFIE',
    ANNULE = 'ANNULE',
    REALISE = 'REALISE',
    FACTURE = 'FACTURE',
    PAYE = 'PAYE'
}

export interface ISeance {
    id?: number;
    dateDebut?: Moment;
    dateFin?: Moment;
    origine?: string;
    domicile?: boolean;
    ticketModerateur?: boolean;
    bilan?: boolean;
    etat?: EtatSeance;
    commentaire?: string;
    orthophonisteId?: number;
    priseEnChargeId?: number;
    feuilleSoinsId?: number;
}

export class Seance implements ISeance {
    constructor(
        public id?: number,
        public dateDebut?: Moment,
        public dateFin?: Moment,
        public origine?: string,
        public domicile?: boolean,
        public ticketModerateur?: boolean,
        public bilan?: boolean,
        public etat?: EtatSeance,
        public commentaire?: string,
        public orthophonisteId?: number,
        public priseEnChargeId?: number,
        public feuilleSoinsId?: number
    ) {
        this.domicile = this.domicile || false;
        this.ticketModerateur = this.ticketModerateur || false;
        this.bilan = this.bilan || false;
    }
}
