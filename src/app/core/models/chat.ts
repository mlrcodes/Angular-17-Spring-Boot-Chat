import { Contact } from "./contac";
import { Message } from "./message";

export interface Chat {
    chatId?: Number;
    contact: Contact
    messages?: Message[];
}
    