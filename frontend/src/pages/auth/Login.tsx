import { motion } from "motion/react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Link, useNavigate } from "react-router";
import { useLoginMutation } from "@/redux/api/authSlice";
import { useState } from "react";
import { Spinner } from "@/components/ui/spinner";
import { toast } from "@/components/ui/toast";
import type { LoginType } from "@/types/auth";

const Login = () => {
    const [user,setUser]=useState<LoginType>({
        username:"",
        password:""
    })
    const navigate=useNavigate();
    const [mutate,{isLoading,isSuccess,error}]=useLoginMutation();
    error?.data&&toast.add({type:"error",title:error?.data?.message})
    if (isSuccess) navigate("/")
    return (
            <motion.div
            key={"login"}
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            className="w-full max-w-md border border-border rounded-2xl p-6 bg-background"
            >

            <h2 className="text-2xl font-bold mb-6 text-center">
                Login
            </h2>

            <form className="space-y-4">

                <Input
                placeholder="Username"
                className="py-5"
                onChange={(e)=>setUser(prev=>({...prev,username:e.target.value}))}
                value={user.username}
                autoComplete="username"
                />

                <Input
                type="password"
                placeholder="Password"
                className="py-5"
                onChange={(e)=>setUser(prev=>({...prev,password:e.target.value}))}
                value={user.password}
                autoComplete="current-password"
                />
                <div className="flex justify-end">
                    <Link to="/forgot-password" className="text-blue-500 text-sm">Forgot Password</Link>
                </div>
                <Button className="w-full" onClick={()=>mutate(user)}>{isLoading?<><Spinner/>Logging in...</>:"Login"}</Button>
            </form>
            <p className="text-sm text-center mt-6 text-muted-foreground">
                Don't have an account?
                <Link to="/signup" className="ml-2 text-foreground font-medium">SignUp</Link>
            </p>

            </motion.div>
    )
}

export default Login