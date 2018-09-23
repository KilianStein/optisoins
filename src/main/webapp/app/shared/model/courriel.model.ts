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
}

export class Courriel implements ICourriel {
    constructor(public id?: number, public email?: string, public type?: TypeCourriel, public commentaire?: string) {}
}
