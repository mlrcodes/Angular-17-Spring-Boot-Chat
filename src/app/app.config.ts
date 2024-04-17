import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideAnimationsAsync } from "@angular/platform-browser/animations/async"
import { tokenInterceptor } from './core/interceptors/token-interceptor.interceptor';


export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideHttpClient(withInterceptors([tokenInterceptor])), provideAnimationsAsync()]
};
