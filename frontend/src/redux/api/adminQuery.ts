import { ADMIN_API_URL } from "../constants";
import { baseApi } from "./apiSlice";

const adminQuery=baseApi.injectEndpoints({
    endpoints:(build)=>({
        getAdminData:build.query({
            query:({pageNumber,page})=>({
                url:`${ADMIN_API_URL}/${page}?page=${pageNumber}`
            }),
            providesTags:[`admin-achievements`]
        }),
        createAdminData:build.mutation({
            query:({page,payload})=>({
                url:`${ADMIN_API_URL}/${page}`,
                method:"POST",
                body:payload
            }),
            invalidatesTags:["admin-achievements"]
        }),
        updateAdminData:build.mutation({
            query:({page,id,payload})=>({
                url:`${ADMIN_API_URL}/${page}/${id}`,
                method:"PUT",
                body:payload
            }),
            invalidatesTags:["admin-achievements"]
        }),
        deleteAdminData:build.mutation({
            query:({page,id})=>({
                url:`${ADMIN_API_URL}/${page}/${id}`,
                method:"DELETE",
            }),
            invalidatesTags:["admin-achievements"]
        })
    })
})

export const {useGetAdminDataQuery,useCreateAdminDataMutation,useDeleteAdminDataMutation,useUpdateAdminDataMutation}=adminQuery;