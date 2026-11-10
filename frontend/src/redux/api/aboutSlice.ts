import { ABOUT_API_URL } from "../constants";
import { baseApi } from "./apiSlice";

const aboutSlice=baseApi.injectEndpoints({
    endpoints:(build)=>({
        getAbouts:build.query({
            query:()=>({
                url:`${ABOUT_API_URL}`
            })
        })
    })
})

export const {useGetAboutsQuery}=aboutSlice;