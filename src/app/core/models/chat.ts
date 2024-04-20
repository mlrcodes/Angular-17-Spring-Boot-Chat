import { Message } from "./message";
import { User } from "./user";

export interface Chat {
    chatId?: Number;
    participants: User[];
    messages: Message[];
}
