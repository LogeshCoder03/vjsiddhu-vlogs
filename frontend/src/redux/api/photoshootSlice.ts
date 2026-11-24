import type { PageParams, PaginationType } from "@/types/comman";
import { ADMIN_API_URL, PHOTOSHOOT_API_URL } from "../constants";
import { baseApi } from "./apiSlice";
import type { PhotoShootType } from "@/types/photoShoot";

const photoShootSlice=baseApi.injectEndpoints({
    endpoints:(builder)=>({
        getPhotShoots:builder.query<PaginationType<PhotoShootType[]>,PageParams>({
            query:({page,size})=>({
                url:`${PHOTOSHOOT_API_URL}?page=${page}&size=${size}`,
            }),
            providesTags:["photoshoots"]
        }),
        addPhotoShoots:builder.mutation({
            query:(formData:FormData)=>({
                url:PHOTOSHOOT_API_URL,
                method:"POST",
                body:formData,
            }),
            invalidatesTags:["photoshoots"]
        }),
        removePhotoShoots:builder.mutation({
            query:(id:number)=>({
                url:`${PHOTOSHOOT_API_URL}/${id}`,
                method:"DELETE",
            }),
            invalidatesTags:["photoshoots"]
        }),
        getAdminPhotoshoots:builder.query({
            query:({page,size})=>({
                url:`${ADMIN_API_URL}${PHOTOSHOOT_API_URL}?page=${page}&size=${size}`,
                credentials:"include"
            }),
            providesTags:["admin-photoshoots"]
        }),
        publishPhotoshoots:builder.mutation({
            query:(id:number)=>({
                method:"PATCH",
                url:`${ADMIN_API_URL}${PHOTOSHOOT_API_URL}/publish/${id}`,
                credentials:"include"
            }),
            invalidatesTags:["admin-photoshoots"]
        }),
        unPublishPhotoshoots:builder.mutation({
            query:(id:number)=>({
                method:"PATCH",
                url:`${ADMIN_API_URL}${PHOTOSHOOT_API_URL}/unpublish/${id}`,
                credentials:"include"
            }),
            invalidatesTags:["admin-photoshoots"]
        }),
    })
})

export const {useAddPhotoShootsMutation,
    useGetPhotShootsQuery,
    useRemovePhotoShootsMutation,useGetAdminPhotoshootsQuery,usePublishPhotoshootsMutation,useUnPublishPhotoshootsMutation}=photoShootSlice;