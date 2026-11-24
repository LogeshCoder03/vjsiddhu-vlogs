export interface LoginType{
    username:string,
    password:string
}

export interface Authority{
    authority:string
}

export interface CurrentUser{
    username:string
    roles:Authority[]
}