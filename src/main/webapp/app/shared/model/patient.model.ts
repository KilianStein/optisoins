import { Moment } from 'moment';
import { IPriseEnCharge } from 'app/shared/model//prise-en-charge.model';
import { ITelephone } from 'app/shared/model//telephone.model';
import { IAdresse } from 'app/shared/model//adresse.model';
import { ICourriel } from 'app/shared/model//courriel.model';

export const enum SituationPatient {
    SALARIE = 'SALARIE',
    TRAVAILLEUR_INDEPENDANT = 'TRAVAILLEUR_INDEPENDANT',
    FONCTIONNAIRE = 'FONCTIONNAIRE',
    RETRAITE = 'RETRAITE',
    SCOLAIRE = 'SCOLAIRE',
    INACTIF = 'INACTIF'
}

export interface IPatient {
    id?: number;
    prenom?: string;
    nom?: string;
    numeroCafat?: string;
    dateNaissance?: Moment;
    employeur?: string;
    situation?: SituationPatient;
    patienteleId?: number;
    assureId?: number;
    prisesEnCharges?: IPriseEnCharge[];
    telephones?: ITelephone[];
    adresses?: IAdresse[];
    courriels?: ICourriel[];
}

export class Patient implements IPatient {
    constructor(
        public id?: number,
        public prenom?: string,
        public nom?: string,
        public numeroCafat?: string,
        public dateNaissance?: Moment,
        public employeur?: string,
        public situation?: SituationPatient,
        public patienteleId?: number,
        public assureId?: number,
        public prisesEnCharges?: IPriseEnCharge[],
        public telephones?: ITelephone[],
        public adresses?: IAdresse[],
        public courriels?: ICourriel[]
    ) {}
}
