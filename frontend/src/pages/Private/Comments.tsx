import { Heart, Trash2Icon } from "lucide-react";
import { Button } from "@/components/ui/button";
import { useCreateCommentMutation, useDeleteCommentMutation, useGetCommentsQuery, useLikeCommentMutation } from "@/redux/api/commentsSlice";
import { useParams } from "react-router";
import { useGetPostQuery } from "@/redux/api/postsSlice";
import { useState } from "react";
import { Input } from "@/components/ui/input";
import Loader from "@/components/Loader";
import { Spinner } from "@/components/ui/spinner";
// import { toast } from "@/components/ui/toast";


export default function PostDetail() {
    const [comment,setComment]=useState({comment:""})
    const {id}=useParams<{id:string}>();
    const{data:comments,isLoading:commentLoading}=useGetCommentsQuery({page:0,size:20,postId:id!});
    const {data:post,isLoading:postLoading}=useGetPostQuery({id:id!});
    const [create,{isLoading:createLoading,error:createError,data:createData}]=useCreateCommentMutation();
    const [like,{error:likeError}]=useLikeCommentMutation()
    const [deleteComment,{isLoading:deleteLoading,error:deleteError,data:deleteData}]=useDeleteCommentMutation();
    if (commentLoading||postLoading) {
        return <Loader/>
    }
    console.log(likeError);
    
    // createError?toast.error(createError?.data?.message):deleteError&&toast.error(deleteError?.data?.message)
    // createData?toast.success(createData?.message):deleteData&&toast.success(deleteData?.message)
    return (
        <section className="max-w-2xl mx-auto px-6 py-20">

        {/* Post Content */}
        <div className="border border-border rounded-2xl p-5 mb-6 bg-background">
            <h2 className="font-semibold text-lg mb-2">{post?.title}</h2>
            <p className="text-sm text-muted-foreground">{post?.content}</p>
        </div>

        {/* Add Comment */}
        <div className="flex gap-3 mb-6">
            <Input
            value={comment.comment}
            onChange={(e) => setComment({comment:e.target.value})}
            placeholder="Write a comment..."
            className="flex-1"
            />
            <Button onClick={()=>create({payload:comment,postId:id})}>
                {createLoading?<><Spinner/>Posting...</>:"Post"}
            </Button>
        </div>

        {/* Comments */}
        <div className="space-y-4">
            {comments?.map((c) => (
            <div
                key={c.id}
                className="border border-border rounded-xl p-4 flex justify-between items-start"
            >

                {/* Left */}                    
                <div>
                    <p className="font-medium text-sm">{c.username}</p>
                    <p className="text-sm text-muted-foreground">{c.comment}</p>
                </div>

                {/* Like */}
                <div className="flex justify-between gap-2">
                    <Button
                    variant="outline"
                    size="icon-lg"
                    onClick={() => like(c.id)}
                    
                    >
                    <Heart size={16} />
                    {c.likes}
                    </Button>
                    <Button variant="outline" size="icon-lg" onClick={()=>deleteComment({postId:post?.id,id:c.id})}>
                        {deleteLoading?<><Spinner/></>:<Trash2Icon size={18}/>}
                    </Button>
                </div>
            </div>
            ))}
        </div>

        </section>
    );
}