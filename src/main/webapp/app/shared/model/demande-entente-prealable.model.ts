export interface IDemandeEntentePrealable {
    id?: number;
    numeroACP?: string;
    nombreSeances?: number;
    ordonnanceId?: number;
    amoId?: number;
}

export class DemandeEntentePrealable implements IDemandeEntentePrealable {
    constructor(
        public id?: number,
        public numeroACP?: string,
        public nombreSeances?: number,
        public ordonnanceId?: number,
        public amoId?: number
    ) {}
}
