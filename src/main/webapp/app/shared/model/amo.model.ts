export interface IAmo {
    id?: number;
    code?: string;
    tarif?: number;
}

export class Amo implements IAmo {
    constructor(public id?: number, public code?: string, public tarif?: number) {}
}
