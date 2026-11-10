import {motion} from "motion/react";
import {Button} from "@/components/ui/button.tsx";
import {Calendar, MapPin} from "lucide-react";
import ErrorFallback from "../ErrorFallback";
import type { HomePageProps } from "@/pages/Home";
import type { EventType } from "@/types/event";
import { Skeleton } from "../ui/skeleton";

const EventSkeleton = () => {
  return (
        <div className="max-w-md w-full rounded-2xl border border-border">
            <Skeleton className="h-50 w-full mb-3"/>
            <Skeleton className="h-4 w-60 mb-2"/>
            <div className="flex justify-between">
                <Skeleton className="w-30 h-4"/>
                <Skeleton className="w-30 h-4"/>
            </div>
            <Skeleton className="m-4 h-7 w-90"/>
        </div>
  )
}

const EventsSection = ({data:events,isLoading,error,onNavigate}:HomePageProps<EventType>) => {
    if (error) return <ErrorFallback message={error.error}/>
    if (events?.content) {
        return 
    }
    return (
        <section className="max-w-7xl mx-auto px-6 py-20">

            {/* Heading */}
            <div className="flex items-center justify-between mb-12">
                <h2 className="text-3xl md:text-4xl font-bold">
                    Events
                </h2>
                <button className="text-sm text-muted-foreground hover:text-foreground">
                    View All →
                </button>
            </div>

            {/* Grid */}
            <div className="grid gap-8 sm:grid-cols-2 lg:grid-cols-3">
                {isLoading?(
                    Array.from({length:3}).map((_,idx)=>(
                        <EventSkeleton key={idx}/>
                    ))
                ):events?.content?.map((event, i) => (
                    <motion.div
                        key={i}
                        initial={{ opacity: 0, y: 30 }}
                        whileInView={{ opacity: 1, y: 0 }}
                        transition={{ delay: i * 0.1 }}
                        viewport={{ once: true }}
                        className="group rounded-2xl overflow-hidden border border-border bg-background cursor-pointer"
                    >

                        {/* Image */}
                        <div className="relative overflow-hidden">
                            <img
                                src={event.logoPath}
                                alt={event.name}
                                className="w-full h-56 object-cover group-hover:scale-105 transition duration-500"
                            />

                            {/* Status Badge */}
                            <span className={`absolute top-3 left-3 text-xs px-3 py-1 rounded-full 
                ${event.status ==1
                                ? "bg-green-500 text-white"
                                : "bg-white/80 text-black"}`}>
                {event.status}
              </span>
                        </div>

                        {/* Content */}
                        <div className="p-5 space-y-3">
                            <h3 className="font-semibold text-lg leading-snug">
                                {event.name}
                            </h3>

                            {/* Info */}
                            <div className="flex flex-col gap-2 text-sm text-muted-foreground">
                                <div className="flex items-center gap-2">
                                    <Calendar size={16} />
                                    {new Date(event.timing).toDateString()}
                                </div>
                                <div className="flex items-center gap-2">
                                    <MapPin size={16} />
                                    {event.venue.city}
                                </div>
                            </div>

                            {/* CTA */}
                            <Button className="w-full mt-3" onClick={()=>onNavigate?.(event.id)}>
                                {event.status === 2 ? "Join Now" : "View Details"}
                            </Button>
                        </div>

                    </motion.div>
                ))}
            </div>

        </section>
    );
};

export default EventsSection;