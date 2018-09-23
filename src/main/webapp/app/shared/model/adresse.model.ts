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
        public nonValide?: boolean
    ) {
        this.nonValide = this.nonValide || false;
    }
}
