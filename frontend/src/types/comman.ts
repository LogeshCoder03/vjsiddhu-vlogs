export interface AboutType{
    id:number;
    name:string;
    about:string;
    role:string;
    imagePath?:string
    instagram:string
}

export interface CreateContactType{
    name:string,
    message:string,
    email:string
}