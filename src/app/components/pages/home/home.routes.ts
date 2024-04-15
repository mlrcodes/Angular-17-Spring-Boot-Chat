import { Routes } from "@angular/router";
import { HomeComponent } from "./home.component";
import { ContactsComponent } from "./contacts/contacts.component";
import { ChatComponent } from "./chat/chat.component";
import { HomeLayerComponent } from "./home-layer/home-layer.component";



export const HOME_ROUTES: Routes = [
    {
        path: '', component: HomeComponent, children: [
            {
                path: '', component: HomeLayerComponent
            },
            {
                path: 'contacts', component: ContactsComponent
            },
            {
                path: 'chat', component: ChatComponent
            }
        ]
    }
]