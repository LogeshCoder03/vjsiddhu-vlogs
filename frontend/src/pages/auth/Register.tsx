import { motion } from "motion/react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Link, useNavigate } from "react-router";
import { useRegisterMutation } from "@/redux/api/authSlice";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Spinner } from "@/components/ui/spinner";
import { SignUpSchema, type SignUpType } from "@/validations/auth";
import { toast } from "@/components/ui/toast";

const Register = () => {
    const [mutate,{isLoading,data,error,isSuccess}]=useRegisterMutation();
    const navigate=useNavigate();
    const {handleSubmit,register,formState:{errors}}=useForm({
        resolver:zodResolver(SignUpSchema),
        defaultValues:{},
        mode:"onChange"
    })
    
    if (isSuccess){
        navigate("/signin")
    } 
    const onSubmit=async (payload:SignUpType) => {
        mutate(payload);
    }
    if(data)toast.add({type:"error",title:data?.message});
    if(error)toast.add({type:"error",title:error?.data?.message});
    return(
        <motion.div
          key={"login"}
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="w-full max-w-md border border-border rounded-2xl p-6 bg-background"
        >

          {/* Title */}
          <h2 className="text-2xl font-bold mb-6 text-center">
            Login
          </h2>

          {/* Form */}
          <form className="space-y-4" onSubmit={handleSubmit(onSubmit)}>

              <Input
              {...register("username")}
                placeholder="Full Name"
                className="py-5"
              />
            {errors.username&&<p className="text-destructive">{errors.username.message}</p>}
            <Input
              {...register("email")}
              placeholder="Email"
            className="py-5"
            />
            {errors.email&&<p className="text-destructive">{errors.email.message}</p>}

            <Input
              {...register("password")}
              placeholder="Password"
            className="py-5"
            />
            {errors.password&&<p className="text-destructive">{errors.password.message}</p>}

              <Input
                {...register("confirmPassword")}
                placeholder="Confirm Password"
                className="py-5"
              />
            {errors.confirmPassword&&<p className="text-destructive">{errors.confirmPassword.message}</p>}


            <Button type="submit" className="w-full">{isLoading?<><Spinner/>Signing in...</>:"Register"}</Button>
          </form>

          {/* Toggle */}
          <p className="text-sm text-center mt-6 text-muted-foreground">
            Already have an account?
            <Link to="/signin"className="ml-2 text-foreground font-medium">Sign up</Link>
          </p>

        </motion.div>
    )
}

export default Register