export const enum TypeTelephone {
    PERSONNEL = 'PERSONNEL',
    PROFESSIONEL = 'PROFESSIONEL',
    DOMICILE = 'DOMICILE',
    BUREAU = 'BUREAU',
    MOBILE = 'MOBILE',
    FIXE = 'FIXE',
    PRINCIPAL = 'PRINCIPAL',
    AUTRE = 'AUTRE'
}

export interface ITelephone {
    id?: number;
    numeroTelephone?: string;
    type?: TypeTelephone;
    commentaire?: string;
    orthophonisteId?: number;
    patientId?: number;
    medecinId?: number;
}

export class Telephone implements ITelephone {
    constructor(
        public id?: number,
        public numeroTelephone?: string,
        public type?: TypeTelephone,
        public commentaire?: string,
        public orthophonisteId?: number,
        public patientId?: number,
        public medecinId?: number
    ) {}
}
