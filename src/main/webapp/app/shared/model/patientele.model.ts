import { IPatient } from 'app/shared/model//patient.model';
import { IRemplacante } from 'app/shared/model//remplacante.model';

export interface IPatientele {
    id?: number;
    nom?: string;
    description?: string;
    orthophonisteId?: number;
    titulaireId?: number;
    patients?: IPatient[];
    remplacantes?: IRemplacante[];
}

export class Patientele implements IPatientele {
    constructor(
        public id?: number,
        public nom?: string,
        public description?: string,
        public orthophonisteId?: number,
        public titulaireId?: number,
        public patients?: IPatient[],
        public remplacantes?: IRemplacante[]
    ) {}
}
