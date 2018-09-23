import { IAdresse } from 'app/shared/model//adresse.model';
import { ICourriel } from 'app/shared/model//courriel.model';
import { ITelephone } from 'app/shared/model//telephone.model';

export interface IMedecin {
    id?: number;
    nom?: string;
    identifiant?: string;
    adresses?: IAdresse[];
    courriels?: ICourriel[];
    telephones?: ITelephone[];
}

export class Medecin implements IMedecin {
    constructor(
        public id?: number,
        public nom?: string,
        public identifiant?: string,
        public adresses?: IAdresse[],
        public courriels?: ICourriel[],
        public telephones?: ITelephone[]
    ) {}
}
