import { ADMIN_API_URL, UNSEEN_CLIPS_API_URL } from "../constants";
import { baseApi } from "./apiSlice";

const unseenClipsSlice=baseApi.injectEndpoints({
    endpoints:(builder)=>({
        getUnseenClips:builder.query({
            query:({page,size})=>({
                url:`${UNSEEN_CLIPS_API_URL}?page=${page}&size=${size}`,
                credentials:"include",
            }),
            providesTags:["unseen-clips"]
        }),
        addUnseenClips:builder.mutation({
            query:(formData:FormData)=>({
                url:UNSEEN_CLIPS_API_URL,
                method:"POST",
                body:formData,
                credentials:"include"
            }),
            invalidatesTags:["unseen-clips"]
        }),
        removeUnseenClips:builder.mutation({
            query:(id:number)=>({
                url:`${UNSEEN_CLIPS_API_URL}/${id}`,
                method:"DELETE"
            }),
            invalidatesTags:["unseen-clips"]
        }),
        getAdminClips:builder.query({
            query:({page,size})=>({
                url:`${ADMIN_API_URL}/clips?page=${page}&size=${size}`,
            }),
            providesTags:["admin-clips"]
        }),
        publishClips:builder.mutation({
            query:(id:number)=>({
                method:"PATCH",
                url:`${ADMIN_API_URL}/clips/publish/${id}`,
            }),
            invalidatesTags:["admin-clips"]
        }),
        unPublishClips:builder.mutation({
            query:(id:number)=>({
                method:"PATCH",
                url:`${ADMIN_API_URL}/clips/unpublish/${id}`,
            }),
            invalidatesTags:["admin-clips"]
        }),
    })
})

export const {useAddUnseenClipsMutation,
    useGetUnseenClipsQuery,
    useRemoveUnseenClipsMutation,    useGetAdminClipsQuery,
    usePublishClipsMutation,
    useUnPublishClipsMutation}=unseenClipsSlice;