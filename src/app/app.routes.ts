import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '', redirectTo: 'home', pathMatch: 'full'
    },
    {
        path: 'auth', loadChildren: () => import('./components/pages/auth/auth.routes').then(c => c.AUTH_ROUTES)
    },
    {
        path: 'home', loadChildren: () => import('./components/pages/home/home.routes').then(c => c.HOME_ROUTES)
    },
];
