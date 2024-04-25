export interface Message {
    messageId?: Number;
    messageText: string;
    senderPhoneNumber: string;
    recipientPhoneNumber: string;
    timestamp: Date;
}
