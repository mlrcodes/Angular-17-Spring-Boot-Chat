import { User } from "./user";

export interface Contact {
    id?: Number;
    contactName: string;
    owner: User;
    contactUser: User;
}