export interface Message {
    messageId?: Number;
    chatId: number;
    senderPhoneNumber: string;
    recipientPhoneNumber: string;
    messageText: string;
    timestamp: Date;
}
