import { Message } from "./message";
import { User } from "./user";

export interface Chat {
    id?: Number;
    participants: User[];
    messages: Message[];
}
