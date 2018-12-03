export const enum TypeCourriel {
    PERSONNEL = 'PERSONNEL',
    PROFESSIONEL = 'PROFESSIONEL',
    AUTRE = 'AUTRE'
}

export interface ICourriel {
    id?: number;
    email?: string;
    type?: TypeCourriel;
    commentaire?: string;
    orthophonisteId?: number;
    patientId?: number;
    medecinId?: number;
}

export class Courriel implements ICourriel {
    constructor(
        public id?: number,
        public email?: string,
        public type?: TypeCourriel,
        public commentaire?: string,
        public orthophonisteId?: number,
        public patientId?: number,
        public medecinId?: number
    ) {}
}
