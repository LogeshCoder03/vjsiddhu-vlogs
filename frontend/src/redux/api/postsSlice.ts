import { POSTS_API_URL } from "../constants";
import {baseApi} from "./apiSlice";

const postSlice=baseApi.injectEndpoints({
    endpoints:(build)=>({
        getPosts:build.query({
            query:()=>({
                url:POSTS_API_URL,
                credentials:"include"
            }),
            providesTags:["posts"]
        }),
        getPost:build.query({
            query:(id:number)=>({
                url:`${POSTS_API_URL}/${id}`,
                credentials:"include"
            }),
            providesTags:["posts"]
        }),
        createPost:build.mutation({
            query:(formData:FormData)=>({
                url:POSTS_API_URL,
                method:"POST",
                credentials:"include",
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
                credentials:"include"
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