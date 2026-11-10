import {baseApi} from "@/redux/api/apiSlice";
import {CONTACT_API_URL} from "@/redux/constants.ts";

export const contactSlice=baseApi.injectEndpoints({
    endpoints:(builder)=>({
        createContact:builder.mutation({
            query:(payload)=> {
                return {
                    url:CONTACT_API_URL,
                    method:"POST",
                    headers:{
                        "Content-Type":"application/json"
                    },
                    body:payload,
                    credentials:"include"
                }
            },
        })
    })
})
export const {useCreateContactMutation}=contactSlice;