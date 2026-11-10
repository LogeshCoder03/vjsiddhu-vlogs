import { COMMENTS_API_URL } from "../constants";
import { baseApi } from "./apiSlice";

const commentsSlice=baseApi.injectEndpoints({
    endpoints:(build)=>({
        getComments:build.query({
            query:({page,size,postId})=>{
                return{ url:`${COMMENTS_API_URL}/${postId}?page=${page}&size=${size}`,}
            },
            providesTags:["comments"]
        }),
        createComment:build.mutation({
            query:({payload,postId})=>({
                url:`${COMMENTS_API_URL}/${postId}`,
                method:"POST",
                body:payload
            }),
            invalidatesTags:["comments"]
        }),
        deleteComment:build.mutation({
            query:({postId,id})=>({
                url:`${COMMENTS_API_URL}/${id}?post_id=${postId}`,
                method:"DELETE",
                credentials:"include"
            }),
            invalidatesTags:["comments"]
        }),
        likeComment:build.mutation({
            query:(commentId)=>({
                url:`${COMMENTS_API_URL}/like/${commentId}`,
                method:"POST",
            }),
            invalidatesTags:["comments"]
        })
    })
})

export const {useGetCommentsQuery,
            useCreateCommentMutation,
            useDeleteCommentMutation,
            useLikeCommentMutation}=commentsSlice;