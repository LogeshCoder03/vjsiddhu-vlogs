import type { LoginType, SignUpType } from "@/lib/types";
import { AUTH_API_URL } from "../constants";
import {baseApi} from "./apiSlice";

const authSlice=baseApi.injectEndpoints({
    endpoints:(build)=>({
        register:build.mutation({
            query:(payload:SignUpType)=>({
                url:`${AUTH_API_URL}/signup`,
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body:payload,
            }),
        }),
        login:build.mutation({
            query:(payload:LoginType)=>({
                url:`${AUTH_API_URL}/signin`,
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body:JSON.stringify(payload),
                credentials:"include"
            }),
            invalidatesTags:["auth"]
        }),
        getCurrentUser:build.query({
            query:()=>({
                url:`${AUTH_API_URL}/me`,
                credentials:"include",
            }),
            providesTags:["auth"]
        }),
        logout:build.mutation({
            query:()=>({
                url:`${AUTH_API_URL}/logout`,
                method:"POST",
            }),
            invalidatesTags:["auth"]
        }),
        forgotPassword:build.mutation({
            query:(payload)=>({
                method:"POST",
                url:`${AUTH_API_URL}/forgot-password`,
                body:payload
            })
        }),
        resetPassword:build.mutation({
            query:({token,payload})=>({
                method:"POST",
                url:`${AUTH_API_URL}/reset-password?token=${token}`,
                body:payload
            })
        }),
    }),
})

export const {useRegisterMutation,
    useLoginMutation,
    useGetCurrentUserQuery,
    useLogoutMutation,
    useForgotPasswordMutation,
    useResetPasswordMutation}=authSlice;