import { Button } from '@/components/ui/button';
import { Card, CardAction, CardContent, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { useCreateBookingsMutation } from '@/redux/api/bookingSlice';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { useParams } from 'react-router';
import { Spinner } from '@/components/ui/spinner';
import { toast } from '@/components/ui/toast';
import { FieldError } from '@/components/ui/field';
import { BookingSchema, type BookingType } from '@/validations/bookings';

const Booking = () => {
    const { type } = useParams();
    const types=["photoshoot","editing"]
    const {handleSubmit,register,reset,formState:{errors,isValid}}=useForm({
        resolver:zodResolver(BookingSchema),
        mode:"all"
    })
    const [create,{error,isLoading,data}]=useCreateBookingsMutation();
    const onCreate=(payload:BookingType)=>{
        create({payload,eventType:type?.toUpperCase()})
    }
    if(error)toast.add({type:"error",title:error?.data?.message})
    if(data)toast.add({type:"success",title:data?.message})
    if (!types.includes(type!)) return;
    return(
        <div className="flex py-10 items-center justify-center">
            <Card className='w-full max-w-md'>
                <CardHeader>
                    <CardTitle>Book For {type?.slice(0,1).toUpperCase().concat(type.slice(1,type.length))}</CardTitle>
                </CardHeader>
                <CardContent className='space-y-2'>
                    <div className="flex flex-col justify-center space-y-2">
                        <Input className='py-5' placeholder='Mobile Number' type='number' {...register("mobileNumber",{valueAsNumber:true})}/>
                        {errors.mobileNumber&&<FieldError>{errors.mobileNumber.message}</FieldError>}
                    </div>
                    <div className="flex flex-col justify-center space-y-2">
                        <Input className='py-5' type='datetime-local' placeholder='Held On' {...register("heldOn")}/>
                        {errors.heldOn&&<FieldError>{errors.heldOn.message}</FieldError>}
                    </div>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-2">
                        <Input placeholder="City" {...register("address.city")}/>
                        {errors.address?.city&&<FieldError>{errors.address.city.message}</FieldError>}
                        <Input placeholder="State" {...register("address.state")}/>
                        {errors.address?.state&&<FieldError>{errors.address.state.message}</FieldError>}
                    </div>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-2">
                        <Input placeholder="Country" {...register("address.district")}/>
                        {errors.address?.district&&<FieldError>{errors.address.district.message}</FieldError>}
                        <Input placeholder="pinCode" type="number" {...register("address.pinCode",{valueAsNumber:true})}/>
                        {errors.address?.pinCode&&<FieldError>{errors.address.pinCode.message}</FieldError>}
                    </div>
                </CardContent>
                <CardFooter className='gap-2'>
                    <CardAction><Button onClick={handleSubmit(onCreate)} disabled={!isValid}>{isLoading?<><Spinner/>Creating...</>:"Create"}</Button></CardAction>
                    <CardAction><Button variant="outline" onClick={()=>reset()}>Cancel</Button></CardAction>
                </CardFooter>
            </Card>
        </div> 
    )

}

export default Booking