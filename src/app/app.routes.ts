import { Routes } from '@angular/router';
import { ChatComponent } from './components/pages/chat/chat.component';

export const routes: Routes = [
    {
        path: '', redirectTo: 'chat', pathMatch: 'full'
    },
    {
        path: 'chat', component: ChatComponent
    },
    {
        path: 'auth', loadChildren: () => import('./components/pages/auth/auth.component').then(c => c.AuthComponent)
    }
];
