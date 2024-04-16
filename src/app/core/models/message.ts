import { User } from "./user";

export interface Message {
    id?: Number;
    content: string;
    sender: User;
    date: Date;
}
