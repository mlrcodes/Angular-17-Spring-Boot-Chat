import {
    AbstractControl,
    ValidationErrors,
    ValidatorFn,
} from '@angular/forms';

export const sqlInjectionValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const message = control.get('message');  

    if (message) {
        const forbiddenPattern = /(\b(select|update|delete|insert|table|from|where|drop table|show tables|\;|\-\-|\=)\b)/gi;
        if (forbiddenPattern.test(message.value)) {
            message.setErrors({ sqlInjectionDetected: true });
            return { sqlInjectionDetected: true };
        }
    }
    return null;
};
