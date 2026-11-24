import { Button } from '@/components/ui/button'
import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Spinner } from '@/components/ui/spinner'
import { toast } from '@/components/ui/toast'
import { useForgotPasswordMutation } from '@/redux/api/authSlice'
import { useState } from 'react'
import { useNavigate } from 'react-router'

const ForgotPassword = () => {
    const [email,setEmail]=useState<string|undefined>()
    const navigate=useNavigate();
    const [mutate,{isLoading}]=useForgotPasswordMutation();
    const handleSubmit=async ()=>{
        try {
            const response=await mutate(email!).unwrap()
            toast.add({type:"error",title:response.message})
            navigate("/signin")
        } catch (error) {
            console.log(error);
            toast.add({type:'error',title:error?.data?.message})
        }
    }
    return (
        <div className="flex items-center justify-center h-96 w-full">
            <Card className='w-full max-w-md'>
                <CardHeader>
                    <CardTitle className='text-center'>Forgot Password</CardTitle>
                </CardHeader>
                <CardContent className='space-y-4'>
                    <Label htmlFor='email'>Email</Label>
                    <Input name='email' value={email} type='email' onChange={(e)=>setEmail(e.target.value)}/>
                </CardContent>
                <CardFooter>
                    <CardAction>
                        <Button onClick={handleSubmit}>{isLoading?<><Spinner/>Sending..</>:"Reset"}</Button>
                    </CardAction>
                </CardFooter>
            </Card>
        </div>
    )
}

export default ForgotPassword