export interface PostType{
    id:number;
    title:string;
    content:string;
    totalLikes:number;
    imagePath:string;
    createdAt:Date;
    updatedAt:Date;
    username:string;
}
export interface CommentType{
    id:number
    comment:string;
    likes:number;
    username:string;
    postId:number;
    createdAt:Date
}