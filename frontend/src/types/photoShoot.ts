// @ts-ignore
export enum ShootType{
    EVENT,
    VLOGS,
    MOVIES,
}
export interface ClipsType{
    id:number;
    type:ShootType;
    videoUrl:string;
    createdAt:Date;
    isPublished:boolean;
    username:string
}
export interface PhotoShootType{
    id:number;
    imagePath:string;
    type:ShootType;
    createdAt:Date;
    isPublished:boolean;
    username:string;
}