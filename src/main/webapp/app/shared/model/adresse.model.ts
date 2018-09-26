export const enum TypeAdresse {
    POSTALE = 'POSTALE',
    DOMICILE = 'DOMICILE',
    AUTRE = 'AUTRE'
}

export interface IAdresse {
    id?: number;
    appartement?: string;
    batiment?: string;
    rue?: string;
    codePostal?: string;
    boitePostale?: string;
    commune?: string;
    type?: TypeAdresse;
    commentaire?: string;
    nonValide?: boolean;
    orthophonisteId?: number;
    patientId?: number;
    medecinId?: number;
}

export class Adresse implements IAdresse {
    constructor(
        public id?: number,
        public appartement?: string,
        public batiment?: string,
        public rue?: string,
        public codePostal?: string,
        public boitePostale?: string,
        public commune?: string,
        public type?: TypeAdresse,
        public commentaire?: string,
        public nonValide?: boolean,
        public orthophonisteId?: number,
        public patientId?: number,
        public medecinId?: number
    ) {
        this.nonValide = this.nonValide || false;
    }
}
