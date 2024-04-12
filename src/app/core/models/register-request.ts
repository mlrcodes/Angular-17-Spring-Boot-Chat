export interface RegisterRequest {
    firstname: string;
    surname: string;
    phoneNumber: string;
    password: string;
    email: string;
    confirmPassword: string;
    acceptedTerms: boolean;
}
