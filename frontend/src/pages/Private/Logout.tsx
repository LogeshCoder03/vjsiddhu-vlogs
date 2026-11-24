import Loader from "@/components/Loader";
import { useLogoutMutation } from "@/redux/api/authSlice"
import { useEffect } from "react";
import { Navigate } from "react-router";

const Logout = () => {
    const [logout,{isLoading,isSuccess,data}]=useLogoutMutation();
    useEffect(()=>{logout()},[])
    if(isLoading)return<Loader/>
    if(isSuccess)return<Navigate to="/"/>
    return <Loader/>
}

export default Logout