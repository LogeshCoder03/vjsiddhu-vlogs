import { motion } from "motion/react";
import { Button } from "@/components/ui/button";
import { Heart, MessageCircle, Share2, Plus, Trash2 } from "lucide-react";
import { useCreatePostMutation, useDeletePostMutation, useGetPostsQuery, useLikePostMutation } from "@/redux/api/postsSlice";
import {
    Dialog,
    DialogTrigger,
    DialogContent,
    DialogDescription,
    DialogTitle,
    DialogClose,
    DialogHeader,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Textarea } from "@/components/ui/textarea";
import { Link } from "react-router";
import { useState } from "react";
import { Spinner } from "@/components/ui/spinner";
import Loader from "@/components/Loader";
import { useGetCurrentUserQuery } from "@/redux/api/authSlice";
import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationLink, PaginationNext, PaginationPrevious } from "@/components/ui/pagination";
import { getPagination } from "@/lib/utils";
import { Empty, EmptyDescription, EmptyHeader, EmptyMedia, EmptyTitle } from "@/components/ui/empty";
import { postSchema, type PostSchemaType } from "@/validations/posts";
import { toast } from "@/components/ui/toast";
import type { PostType } from "@/types/post";

function Posts() {
    const [open,onOpen]=useState<boolean>(false);
    const [pageNumber,setPageNumber]=useState<number>(0);
    const {isLoading,data:posts}=useGetPostsQuery({page:pageNumber,size:6});
    const [createPost,{isLoading:createLoading,error:createError,data:createData}]=useCreatePostMutation()
    const [likePost,{error:likeError}]=useLikePostMutation()
    const [deletePost,{isLoading:deleteLoading,error:deleteError,data:deleteData}]=useDeletePostMutation();
    const {handleSubmit,register,formState:{errors,isValid},setValue,reset}=useForm({
        resolver:zodResolver(postSchema),
        mode:"onChange"
    })
    console.log(likeError);
    
    createError?toast.add({type:"error",title:createError?.data?.message}):deleteError&&toast.add({type:"error",title:deleteError?.data?.message})
    createData?toast.add({type:"success",title:createData.message}):deleteData&&toast.add({type:"success",title:deleteData.message})
    const onCreate=(payload:PostSchemaType)=>{
        const formData=new FormData();
        formData.append("title",payload.title)
        formData.append("content",payload.content)
        formData.append("image",payload?.image!)
        onOpen(false);
        reset()
        createPost(formData);
    }
    const handleLike=(id:number)=>{
        likePost(id);
    }
    
    const pages=getPagination(posts?.number!+1,posts?.totalPages!);
    const {data:user,isLoading:userLoading}=useGetCurrentUserQuery(undefined);
    const isAdmin=user?.roles?.find(role=>role.authority==="USER");
    if(isLoading||userLoading)return<Loader/>;
    return (
        <section className="max-w-3xl mx-auto px-6 py-20">
            {/* Header */}
            <div className="flex items-center justify-between mb-10">
                <h2 className="text-3xl font-bold">Community</h2>
                {isAdmin&&(
                <Dialog open={open} onOpenChange={onOpen}>
                    <DialogTrigger className="flex items-center gap-2 bg-black text-white rounded-md px-2 py-1 cursor-pointer">
                        <Plus size={18} />Create Post
                    </DialogTrigger>
                    <DialogContent>
                        <DialogHeader>
                            <DialogTitle>Create Post</DialogTitle>
                            <DialogDescription>Create Event</DialogDescription>
                        </DialogHeader>
                        <form onSubmit={handleSubmit(onCreate)} className="space-y-4 mt-4">
                            <Input {...register("title")} placeholder="Title"/>
                            {errors.title&&<p className="text-destructive">{errors.title.message}</p>}
                            <Textarea placeholder="Enter the content ofo the post...." {...register("content")}/>
                            {errors.content&&<p className="text-destructive">{errors.content.message}</p>}
                            <Input type="file" onChange={(e)=>setValue("image",e.target.files?.[0])}/>
                            {errors.image&&<p className="text-destructive">{errors.image.message}</p>}
                            <div className="flex items-end gap-3">
                                <Button type="submit" disabled={!isValid}>{createLoading?<><Spinner/>Creating...</>:"Create"}</Button>
                                <DialogClose>Close</DialogClose>
                            </div>
                        </form>
                    </DialogContent>
                </Dialog>
                )}
            </div>

            {/* Posts */}
            {posts?.empty?(
                <Empty>
                    <EmptyHeader>
                        <EmptyMedia variant="icon">
                        {/* <IconFolderCode /> */}
                        </EmptyMedia>
                        <EmptyTitle>No Posts Yet</EmptyTitle>
                        <EmptyDescription>
                        You haven&apos;t created any projects yet. Get started
                        by creating your first project.
                        </EmptyDescription>
                    </EmptyHeader>
                </Empty>
            ):(
            <div className="space-y-8">
                {posts?.content?.map((post:PostType) => (
                    <motion.div
                        key={post.id}
                        initial={{ opacity: 0, y: 30 }}
                        whileInView={{ opacity: 1, y: 0 }}
                        viewport={{ once: true }}
                        className="border border-border rounded-2xl p-5 bg-background"
                    >
                        <div className="flex items-center justify-between mb-3">
                            <div>
                                <p className="font-semibold">{post.title}</p>
                                <p className="text-xs text-muted-foreground">{new Date(post.createdAt).toDateString()}</p>
                            </div>
                            {isAdmin&&
                            <Button variant="destructive" size="icon-lg" onClick={()=>deletePost(post.id)}>{deleteLoading?<Spinner/>:<Trash2 size={18}/>}</Button>
                            }
                        </div>
                        <p className="mb-4 text-sm leading-relaxed">
                            {post.content}
                        </p>
                        {post.imagePath && (
                            <div className="overflow-hidden rounded-xl mb-4">
                                <img
                                    src={post.imagePath}
                                    className="w-full max-h-80 object-cover hover:scale-105 transition duration-500"
                                />
                            </div>
                        )}

                        {/* Actions */}
                        <div className="flex items-center justify-between text-sm text-muted-foreground border-t border-border pt-3">

                            <div className="flex items-center gap-6">
                                <Button size="icon-lg" variant="outline" onClick={()=>handleLike(post.id)}>
                                    <Heart size={18} />
                                    {post.totalLikes}
                                </Button>

                                <button className="flex items-center gap-2 hover:text-foreground">
                                    <MessageCircle size={18} />
                                    {100}
                                </button>

                                <button className="flex items-center gap-2 hover:text-foreground">
                                    <Share2 size={18} />
                                    Share
                                </button>
                            </div>

                        </div>

                        {/* Comment Preview */}
                        <div className="mt-4 text-sm text-muted-foreground">
                            <Link to={`/posts/${post.id}`}>View all comments...</Link>
                        </div>

                    </motion.div>
                ))}
                <Pagination>
                    <PaginationContent>
                        <PaginationItem>
                            <PaginationPrevious onClick={()=>setPageNumber(pageNumber-1)}/>
                        </PaginationItem>
                        {pages.map((p,i)=>(
                            p==="..."?(<PaginationEllipsis key={i}/>):(
                            <PaginationItem key={i}>
                                <PaginationLink isActive={posts?.number!+1===p} onClick={()=>setPageNumber(p as number)}>
                                    {p}
                                </PaginationLink>
                            </PaginationItem>
                            )
                        ))}
                        <PaginationItem>
                            <PaginationNext onClick={()=>setPageNumber(pageNumber+1)}/>
                        </PaginationItem>
                    </PaginationContent>
                </Pagination>
            </div>
            )}

        </section>

    );
}

export default Posts;

