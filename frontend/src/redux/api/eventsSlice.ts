import {baseApi} from "@/redux/api/apiSlice";
import {FEAST_API_URL} from "@/redux/constants.ts";
import type { PageParams, PaginationType } from "@/types/comman";
import type { EventType } from "@/types/event";

export const eventSlice=baseApi.injectEndpoints({
    endpoints:(builder)=>({
        getEvents:builder.query<PaginationType<EventType[]>,PageParams>({
            query:({page,size})=>({
                    url:`${FEAST_API_URL}?page=${page}&size=${size}`
            }),
            providesTags:["events"]
        }),
        createEvent:builder.mutation({
            query:(payload:FormData)=> {
                return {
                    url:FEAST_API_URL,
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:payload,

                }
            },
            invalidatesTags:["events"]
        }),
        getEvent:builder.query<EventType,{id:string}>({
            query:({id})=>({
                    url:`${FEAST_API_URL}/${id}`
            }),
            providesTags:["events"]
        }),
        deleteEvent:builder.mutation({
            query:(id:number)=>({
                url:`${FEAST_API_URL}/${id}`,
                method:"DELETE"
            }),
            invalidatesTags:["events"]
        }),
        updateEvent:builder.mutation({
            query:({formData,id})=>({
                url:`${FEAST_API_URL}/${id}`,
                method:"PUT",
                body:formData,
            }),
            invalidatesTags:["events"]
        }),
        verifyOtp:builder.mutation({
            query:({mobileNumber,otp})=>({
                url:`${FEAST_API_URL}?otp=${otp}&mobileNumber=${mobileNumber}`,
                method:"PUT",
            })
        })
    })
})
export const {useGetEventsQuery,
    useCreateEventMutation,
    useGetEventQuery,
    useDeleteEventMutation,
    useUpdateEventMutation,useVerifyOtpMutation}=eventSlice;