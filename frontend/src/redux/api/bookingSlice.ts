import { EVENT_API_URL } from "../constants";
import { baseApi } from "./apiSlice";

const bookingSlice=baseApi.injectEndpoints({
    endpoints:(builder)=>({
        createBookings:builder.mutation({
            query:({payload,eventType})=>({
                method:"POST",
                url:`${EVENT_API_URL}?event_type=${eventType}`,
                body:payload,
                credentials:"include"
            }),
            invalidatesTags:["user-bookings"]
        }),
        updateBookings:builder.mutation({
            query:({payload,eventId})=>({
                method:"PUT",
                url:`${EVENT_API_URL}/${eventId}`,
                body:payload,
                credentials:"include"
            }),
            invalidatesTags:["user-bookings"]
        }),
        deleteBookings:builder.mutation({
            query:(eventId)=>({
                method:"DELETE",
                url:`${EVENT_API_URL}/${eventId}`,
                credentials:"include"
            }),
            invalidatesTags:["user-bookings"]
        }),
        getUserBookings:builder.query({
            query:({page,size})=>({
                url:`${EVENT_API_URL}?page=${page}&size=${size}`,
                credentials:"include"
            }),
            providesTags:['user-bookings']
        })
    })
})
export const{useCreateBookingsMutation,
    useDeleteBookingsMutation,
    useGetUserBookingsQuery,
    useUpdateBookingsMutation}=bookingSlice;