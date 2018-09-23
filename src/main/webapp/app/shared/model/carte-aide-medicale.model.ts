import { Moment } from 'moment';

export interface ICarteAideMedicale {
    id?: number;
    dateDebutValidite?: Moment;
    dateFinValidite?: Moment;
    numero?: string;
    assureId?: number;
}

export class CarteAideMedicale implements ICarteAideMedicale {
    constructor(
        public id?: number,
        public dateDebutValidite?: Moment,
        public dateFinValidite?: Moment,
        public numero?: string,
        public assureId?: number
    ) {}
}
