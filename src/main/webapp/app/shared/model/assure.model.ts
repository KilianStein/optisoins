import { Moment } from 'moment';
import { ICarteAideMedicale } from 'app/shared/model//carte-aide-medicale.model';
import { IMutuelle } from 'app/shared/model//mutuelle.model';

export const enum TypeLienAssure {
    CONJOINT = 'CONJOINT',
    CONCUBIN = 'CONCUBIN',
    ENFANT = 'ENFANT',
    AUTRE = 'AUTRE'
}

export interface IAssure {
    id?: number;
    nom?: string;
    prenom?: string;
    numeroCafat?: string;
    dateNaissance?: Moment;
    lienAssure?: TypeLienAssure;
    courrielId?: number;
    adresseId?: number;
    telephoneId?: number;
    cartesAideMedicales?: ICarteAideMedicale[];
    mutuelles?: IMutuelle[];
}

export class Assure implements IAssure {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public numeroCafat?: string,
        public dateNaissance?: Moment,
        public lienAssure?: TypeLienAssure,
        public courrielId?: number,
        public adresseId?: number,
        public telephoneId?: number,
        public cartesAideMedicales?: ICarteAideMedicale[],
        public mutuelles?: IMutuelle[]
    ) {}
}
