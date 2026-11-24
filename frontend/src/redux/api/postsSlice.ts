import type { PageParams, PaginationType } from "@/types/comman";
import { POSTS_API_URL } from "../constants";
import {baseApi} from "./apiSlice";
import type { PostType } from "@/types/post";

const postSlice=baseApi.injectEndpoints({
    endpoints:(build)=>({
        getPosts:build.query<PaginationType<PostType[]>,PageParams>({
            query:({page})=>({
                url:POSTS_API_URL,
                params:{
                    page
                }
            }),
            providesTags:["posts"]
        }),
        getPost:build.query<PostType,{id:string}>({
            query:(id)=>({
                url:`${POSTS_API_URL}/${id}`,
            }),
            providesTags:["posts"]
        }),
        createPost:build.mutation({
            query:(formData:FormData)=>({
                url:POSTS_API_URL,
                method:"POST",
                body:formData
            }),
            invalidatesTags:["posts"]
        }),
        deletePost:build.mutation({
            query:(id:number)=>({
                url:`${POSTS_API_URL}/${id}`,
                method:"DELETE",
            }),
            invalidatesTags:["posts"]
        }),
        likePost:build.mutation({
            query:(id:number)=>({
                url:`${POSTS_API_URL}/like/${id}`,
                method:"POST",
            }),
            invalidatesTags:["posts"]
        }),
    }),
})

export const{useGetPostsQuery,
    useCreatePostMutation,
    useGetPostQuery,
    useLikePostMutation,
    useDeletePostMutation}=postSlice;