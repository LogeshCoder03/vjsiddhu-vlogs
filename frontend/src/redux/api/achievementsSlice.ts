import type { PaginationType } from "@/types/comman";
import { ACHIEVEMENT_API_URL } from "../constants";
import {  baseApi } from "./apiSlice";
import type { Achivement } from "@/types/achievement";

const achievementsSlice=baseApi.injectEndpoints({
    endpoints:(builder)=>({
        getAchievements:builder.query<PaginationType<Achivement[]>,null>({
            query:()=>({
                url:ACHIEVEMENT_API_URL,
                credentials:"include",
            }),
        }),
        addAchievements:builder.mutation({
            query:(payload)=>({
                url:ACHIEVEMENT_API_URL,
                method:"POST",
                body:payload
            })
        }),
        removeAchievement:builder.mutation({
            query:(id:number)=>({
                url:`${ACHIEVEMENT_API_URL}/${id}`,
                method:"DELETE"
            })
        })
    })
})

export const {useAddAchievementsMutation,
    useGetAchievementsQuery,
    useRemoveAchievementMutation}=achievementsSlice;