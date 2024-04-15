import { User } from "./user";

export interface Message {
    id?: Number;
    sender: User;
    date: Date;
}
