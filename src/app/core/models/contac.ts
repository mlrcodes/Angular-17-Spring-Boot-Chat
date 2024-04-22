import { User } from "./user";

export interface Contact {
    contactId: number;
    contactName: string;
    contactUser: User;
}