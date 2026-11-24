import { Button } from '@/components/ui/button'
import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { FieldError } from '@/components/ui/field'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Spinner } from '@/components/ui/spinner'
import { toast } from '@/components/ui/toast'
import { useResetPasswordMutation } from '@/redux/api/authSlice'
import { passwordSchema } from '@/validations/auth'
import { useState, type ChangeEvent } from 'react'
import { useNavigate, useSearchParams } from 'react-router'

const ResetPassword = () => {
    const [password,setPassword]=useState<string>("");
    const navigate=useNavigate();
    const [error,setError]=useState<{password?:string,confirmPassword?:string}|undefined>(undefined)
    const [searchParams]=useSearchParams()
    const token=searchParams.get("token")    
    const [mutate,{isLoading,error:resError,data,isSuccess}]=useResetPasswordMutation();
    const handleChange=(event:ChangeEvent<HTMLInputElement>)=>{
        const {value,name}=event.target
        if (name=="password") {
            setPassword(value)
        }
        const result=passwordSchema.safeParse({
            [name]:value
        })
        if (!result.success) {
            const fieldErrors:Record<string,string>={}
            result.error.issues.forEach(issue=>{
                const field=issue.path[0] as "password"|"confirmPassword"
                if (!fieldErrors[field]) {
                    fieldErrors[field]=issue.message
                }
            })
            setError(fieldErrors)
        }else{
            setError(undefined)
        }
    }
    if(resError)toast.add({type:"error",title:resError.data.message})
    if (data) toast.add({type:"success",title:data.message})
    if(isSuccess)navigate("/signin")
    return (
        <div className="flex items-center justify-center h-96 w-full">
            <Card className='w-full max-w-md'>
                <CardHeader>
                    <CardTitle className='text-center'>Reset Password</CardTitle>
                </CardHeader>
                <CardContent className='space-y-2'>
                    <div className="space-y-4">
                        <Label htmlFor='password'>Password</Label>
                        <Input name='password' onChange={handleChange}/>
                        {error&&<FieldError>{error.password}</FieldError>}
                    </div>
                    <div className="space-y-4">
                        <Label htmlFor='confirmPassword'>Confirm Password</Label>
                        <Input name='confirmPassword' onChange={handleChange}/>
                        {error&&<FieldError>{error.confirmPassword}</FieldError>}
                    </div>
                </CardContent>
                <CardFooter>
                    <CardAction>
                        <Button onClick={()=>mutate({token,payload:password})}>{isLoading?<><Spinner/>Changing</>:"Change"}</Button>
                    </CardAction>
                </CardFooter>
            </Card>
        </div>
    )
}

export default ResetPassword