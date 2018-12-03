export interface ICompteBancaire {
    id?: number;
    numeroCompte?: string;
    nomBanque?: string;
}

export class CompteBancaire implements ICompteBancaire {
    constructor(public id?: number, public numeroCompte?: string, public nomBanque?: string) {}
}
