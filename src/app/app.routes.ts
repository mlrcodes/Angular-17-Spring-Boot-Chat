import { Routes } from '@angular/router';
import { NotFoundComponent } from './components/pages/not-found/not-found.component';
import { AuthGuard } from './core/guards/auth.guard';
import { UserChatsResolver } from './core/resolvers/userChats.resolver';

export const routes: Routes = [
    {
        path: '', redirectTo: 'auth', pathMatch: 'full'
    },
    {
        path: 'auth', loadChildren: () => import('./components/pages/auth/auth.routes').then(c => c.AUTH_ROUTES)
    },
    {
        path: 'home', 
        loadChildren: () => import('./components/pages/home/home.routes').then(c => c.HOME_ROUTES),
        resolve: {
            userChats: UserChatsResolver
        }, 
        canActivate: [AuthGuard]
    },
    {
        
        path: '**', component: NotFoundComponent
    },
];
