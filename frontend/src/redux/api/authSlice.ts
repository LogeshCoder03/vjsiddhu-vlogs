import type { SignUpType } from "@/validations/auth";
import { AUTH_API_URL } from "../constants";
import {baseApi} from "./apiSlice";
import type { CurrentUser, LoginType } from "@/types/auth";

const authSlice=baseApi.injectEndpoints({
    endpoints:(build)=>({
        register:build.mutation<any,SignUpType>({
            query:(payload)=>({
                url:`${AUTH_API_URL}/signup`,
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body:payload,
            }),
        }),
        login:build.mutation<any,LoginType>({
            query:(payload)=>({
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
        getCurrentUser:build.query<CurrentUser,undefined>({
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