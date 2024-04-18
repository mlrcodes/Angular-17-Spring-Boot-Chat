// import { HttpErrorResponse, HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
// import { inject } from '@angular/core';
// import { MessageService } from 'primeng/api';
// import { Observable, catchError, throwError } from 'rxjs';

// export const errorsInterceptor: HttpInterceptorFn = (
//   request: HttpRequest<any>,
//   next: HttpHandlerFn
// ): Observable<HttpEvent<any>> => {

//   const messageService = inject(MessageService)

//   return next(request).pipe(

//     catchError((error: HttpErrorResponse) => {
//       let errorMessage = 'Ocurrió un error desconocido';
      
//       if (error.error instanceof ErrorEvent) {
//         errorMessage = `Error del cliente: ${error.error.message}`;
//       } else {
//         errorMessage = `Error ${error.status} al acceder al servidor. ${error.statusText}`;
//       }

//       messageService.add({severity:'error', summary:'Error: ', detail: errorMessage});

//       console.log("Mensaje de error añadido")

//       return throwError(() => new Error(errorMessage));
    
//     })
//   );
// };
