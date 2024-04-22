import { Routes } from "@angular/router";
import { HomeComponent } from "./home.component";
import { ContactsComponent } from "./contacts/contacts.component";
import { ChatsComponent } from "./chats/chats.component";
import { HallsComponent } from "./halls/halls.component";



export const HOME_ROUTES: Routes = [
    {
        path: '', component: HomeComponent, children: [
            {
                path: '', redirectTo: 'chats', pathMatch: 'full'
            },
            {
                path: 'chats', loadChildren: () => import("./chats/chats.routes").then(c => c.CHATS_ROUTES)
            },            
            {
                path: 'contacts', component: ContactsComponent
            },
            {
                path: 'halls', component: HallsComponent
            }
        ]
    }
]