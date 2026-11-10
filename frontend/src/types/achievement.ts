// @ts-ignore
export enum AchievementType{
    AWARD,
    TROPHY,
    STAR
}

export interface Achivement{
    id:number;
    title:string;
    description:string;
    username:string;
    createdAt:Date;
    type:AchievementType
    priority:number
}
