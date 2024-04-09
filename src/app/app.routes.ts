import { Routes } from '@angular/router';
import { ChatComponent } from './components/pages/chat/chat.component';

export const routes: Routes = [
    {
        path: '', redirectTo: 'auth', pathMatch: 'full'
    },
    {
        path: 'auth', loadChildren: () => import('./components/pages/auth/auth.routes').then(c => c.AUTH_ROUTES)
    },
    {
        path: 'chat', component: ChatComponent
    },
];
