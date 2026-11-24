import { motion } from "framer-motion";
import { Button } from "@/components/ui/button";
import { Calendar, MapPin, Users } from "lucide-react";
import { useGetEventQuery } from '@/redux/api/eventsSlice';
import { useParams } from 'react-router';
import { Dialog, DialogClose, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { useState } from "react";
import { useRegisterEventMutation } from "@/redux/api/userEventSlice";
import { Spinner } from "@/components/ui/spinner";
import Loader from "@/components/Loader";
import ErrorFallback from "@/components/ErrorFallback";

const EventDetails = () => {
    const [open,onOpen]=useState<boolean>(false);
    const [otpOpen,setOtpOpen]=useState(false)
    const {id}=useParams<{id:string}>();
    const [data,setData]=useState<{mobileNumber?:number|string|null}>({mobileNumber:null});
    const [error,setError]=useState<string>();
    const {data:event,isLoading,error:fetchError}=useGetEventQuery({id:id!});
    const [registerEvent,{isLoading:createLoading,
        error:createError,
        data:createData,
        isSuccess
    }]=useRegisterEventMutation();
    const onRegister=()=>{
        if (String(data.mobileNumber).length!==10) {
            setError("Mobile number must be 10 digits");
            return;
        }
        registerEvent({payload:data,eventId:id})
        onOpen(false);
        if (isSuccess) {
            setOtpOpen(true)
        }
    }
    // if(createData){toast.success(createData.message)}
    // if(createError)toast.error(createError?.data?.message)
    if (fetchError)return<ErrorFallback message={fetchError.data?.message}/> 
    if(isLoading)return<Loader/>;
    return (
        <section className="max-w-7xl mx-auto px-6 py-20">

            <div className="grid lg:grid-cols-2 gap-10">

                <motion.div
                initial={{ opacity: 0, x: -30 }}
                animate={{ opacity: 1, x: 0 }}
                className="overflow-hidden rounded-2xl"
                >
                    <img
                        src={event?.logoPath}
                        className="w-full h-full object-cover"
                    />
                </motion.div>

                <motion.div
                initial={{ opacity: 0, x: 30 }}
                animate={{ opacity: 1, x: 0 }}
                className="space-y-6"
                >

                    <h1 className="text-4xl font-bold tracking-tight">
                        {event?.name}
                    </h1>
                    <p className="text-muted-foreground leading-relaxed">
                        {event?.description}
                    </p>

                    <div className="space-y-3 text-sm text-muted-foreground">

                        <div className="flex items-center gap-2">
                        <Calendar size={16} />
                        {new Date(event?.timing!).toDateString()}
                        </div>

                        <div className="flex items-center gap-2">
                        <MapPin size={16} />
                        {event?.venue.district}, {event?.venue.city}, {event?.venue.state},{" "}
                        {event?.country} - {event?.venue.pinCode}
                        </div>

                        <div className="flex items-center gap-2">
                        <Users size={16} />
                        {event?.joined} / {event?.totalMembers} people joined
                        </div>

                    </div>

                    <div className="w-full bg-muted rounded-full h-2">
                        <div
                        className="bg-foreground h-2 rounded-full"
                        style={{
                            width: `${(event?.joined / event?.totalMembers!) * 100}%`,
                        }}
                        />
                    </div>
                    {event?.status==="Ongoing"?(
                        <Dialog open={open} onOpenChange={onOpen}>
                            <DialogTrigger className="w-full text-lg rounded-md py-2 text-primary-foreground bg-primary ">Book Now</DialogTrigger>
                            <DialogContent>
                                <DialogHeader>
                                    <DialogTitle>Book The Event</DialogTitle>
                                    <DialogDescription>jncjenkwjenkwjedn</DialogDescription>
                                </DialogHeader>
                                <div className="">
                                    <Input 
                                    type="number"
                                    onChange={(e)=>setData({mobileNumber:e.target.value})}
                                    placeholder="Phone number" 
                                    className="py-5 hover:outline-background"
                                    />
                                    <p className="text-destructive">{error}</p>
                                </div>
                                <div className="flex gap-2 px-4">
                                    <Button onClick={onRegister}>{createLoading?<><Spinner/>Registering...</>:"Register"}</Button>
                                    <DialogClose>Close</DialogClose>
                                </div>
                            </DialogContent>
                        </Dialog>
                    ):event?.status==="Upcoming"?<Button className="w-full font-semibold text-xl py-5">Event Start Soon</Button>:<span className="w-full text-center bg-foreground text-background font-semibold text-xl rounded-2xl px-5 py-2">Finished</span>}

                </motion.div>

            </div>
            <Dialog open={otpOpen} onOpenChange={()=>setOtpOpen(!open)}>
                <DialogContent>
                    <DialogHeader>
                        <DialogTitle>Book The Event</DialogTitle>
                        <DialogDescription>jncjenkwjenkwjedn</DialogDescription>
                    </DialogHeader>
                    <div className="">
                        <Input 
                        type="number"
                        onChange={(e)=>setData({mobileNumber:e.target.value})}
                        placeholder="Phone number" 
                        className="py-5 hover:outline-background"
                        />
                        <p className="text-destructive">{error}</p>
                    </div>
                    <div className="flex gap-2 px-4">
                        <Button onClick={onRegister}>{createLoading?<><Spinner/>Registering...</>:"Register"}</Button>
                        <DialogClose>Close</DialogClose>
                    </div>
                </DialogContent>
            </Dialog>
        </section>
    );
}

export default EventDetails
