import { User } from "./user";

export interface Contact {
    contactId?: number;
    contactName: string;
    owner?: User;
    contactUser: User;
}