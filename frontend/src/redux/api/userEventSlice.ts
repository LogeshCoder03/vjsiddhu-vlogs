import { USER_FEAST_API_URL } from "../constants";
import { baseApi } from "./apiSlice";

const userEventSlice=baseApi.injectEndpoints({
    endpoints:(build)=>({
        registerEvent:build.mutation({
            query:({payload,eventId})=>({
                method:"POST",
                url:`${USER_FEAST_API_URL}?feast_id=${eventId}`,
                body:payload,
            }),
            invalidatesTags:["user-feasts"]
        }),
        getUserEvents:build.query({
            query:({page,size})=>({
                url:`${USER_FEAST_API_URL}?page=${page}&size=${size}`,
            }),
            providesTags:["user-feasts"]
        }),
        deleteUserEvent:build.mutation({
            query:(id:number)=>({
                url:`${USER_FEAST_API_URL}/${id}`,
                method:"DELETE",
            }),
            invalidatesTags:["user-feasts"]
        }),
    })
})

export const {useRegisterEventMutation,useGetUserEventsQuery,useDeleteUserEventMutation}=userEventSlice;