import {configureStore} from "@reduxjs/toolkit";
import {baseApi} from "@/redux/api/apiSlice";
import {setupListeners} from "@reduxjs/toolkit/query";

const store=configureStore({
    reducer:{
        [baseApi.reducerPath]: baseApi.reducer,
    },
    middleware:(getDefaultMiddleware) =>
        getDefaultMiddleware()
    .concat(baseApi.middleware)
})
setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = ReturnType<typeof store.dispatch>

export default store;