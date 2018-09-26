export const enum TypeMutuelle {
    PRINCIPAL = 'PRINCIPAL',
    SECONDAIRE = 'SECONDAIRE'
}

export interface IMutuelle {
    id?: number;
    nom?: string;
    numero?: string;
    type?: TypeMutuelle;
    assureId?: number;
}

export class Mutuelle implements IMutuelle {
    constructor(public id?: number, public nom?: string, public numero?: string, public type?: TypeMutuelle, public assureId?: number) {}
}
