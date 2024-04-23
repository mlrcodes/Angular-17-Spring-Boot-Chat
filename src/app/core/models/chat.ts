import { Contact } from "./contac";
import { Message } from "./message";

export interface Chat {
    chatId: number;
    contact: Contact
    messages?: Message[];
}
    