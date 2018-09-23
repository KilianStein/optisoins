import { Moment } from 'moment';

export interface IRemplacante {
    id?: number;
    dateDebut?: Moment;
    dateFin?: Moment;
    patienteleId?: number;
    orthophonisteId?: number;
}

export class Remplacante implements IRemplacante {
    constructor(
        public id?: number,
        public dateDebut?: Moment,
        public dateFin?: Moment,
        public patienteleId?: number,
        public orthophonisteId?: number
    ) {}
}
