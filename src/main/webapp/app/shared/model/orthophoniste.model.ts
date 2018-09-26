import { IPatientele } from 'app/shared/model//patientele.model';
import { IEtatRecapitulatif } from 'app/shared/model//etat-recapitulatif.model';
import { ITelephone } from 'app/shared/model//telephone.model';
import { IAdresse } from 'app/shared/model//adresse.model';
import { ICourriel } from 'app/shared/model//courriel.model';

export interface IOrthophoniste {
    id?: number;
    nom?: string;
    prenom?: string;
    numeroCafat?: string;
    numeroRidet?: string;
    compteBancaireId?: number;
    patienteles?: IPatientele[];
    etatsRecapitulatifs?: IEtatRecapitulatif[];
    telephones?: ITelephone[];
    adresses?: IAdresse[];
    courriels?: ICourriel[];
}

export class Orthophoniste implements IOrthophoniste {
    constructor(
        public id?: number,
        public nom?: string,
        public prenom?: string,
        public numeroCafat?: string,
        public numeroRidet?: string,
        public compteBancaireId?: number,
        public patienteles?: IPatientele[],
        public etatsRecapitulatifs?: IEtatRecapitulatif[],
        public telephones?: ITelephone[],
        public adresses?: IAdresse[],
        public courriels?: ICourriel[]
    ) {}
}
