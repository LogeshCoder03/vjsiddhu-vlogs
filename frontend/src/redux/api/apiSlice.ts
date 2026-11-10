import { createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import { BASE_API_URL} from "@/redux/constants.ts";

export const baseApi=createApi({
    reducerPath:"api",
    baseQuery:fetchBaseQuery({baseUrl:BASE_API_URL,credentials:"include"}),
    endpoints:()=>({}),
    tagTypes:["posts","comments","auth","events","user-feasts",
        "user-bookings","admin-posts","unseen-clips","photoshoots"]
})
