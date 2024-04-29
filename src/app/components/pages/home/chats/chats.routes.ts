import { Routes } from "@angular/router";
import { ChatComponent } from "../../../resources/chat/chat.component";
import { ChatsComponent } from "./chats.component";

export const CHATS_ROUTES: Routes = [
    {
        path: '', component: ChatsComponent,
    },
    {
        path: 'chat/:chatId', component: ChatComponent
    }
]