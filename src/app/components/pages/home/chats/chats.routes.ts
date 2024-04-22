import { Routes } from "@angular/router";
import { ChatComponent } from "../../../resources/chat/chat.component";

export const CHATS_ROUTES: Routes = [
    {
        path: '', component: ChatComponent, children: [
            {
                path: 'chat', component: ChatComponent
            }
        ]
    }
]