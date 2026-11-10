import HeroSection from "@/components/home/HeroSection.tsx";
import FeatureVideosSection from "@/components/home/FeatureVideosSection.tsx";
import GallerySection from "@/components/home/GallerySection.tsx";
import AboutSection from "@/components/home/AboutSection.tsx";
import AchievementsSection from "@/components/home/AchievementsSection.tsx";
import EventsSection from "@/components/home/EventsSection.tsx";
import {useGetEventsQuery} from "@/redux/api/eventsSlice.ts"
import { useNavigate } from "react-router";
import { useGetAchievementsQuery } from "@/redux/api/achievementsSlice";
import { useGetPhotShootsQuery } from "@/redux/api/photoshootSlice";
import { useGetUnseenClipsQuery } from "@/redux/api/clipsSlice";

export interface HomePageProps<T>{
    data:{content:T[],empty?:boolean},
    isLoading:boolean,
    error:any,
    onNavigate?:(id:number)=>void
}

const Home=()=> {
    const {data:events,isLoading:eventsLoading,error:eventError}=useGetEventsQuery({page:0,size:6});
    const {data:achievements,isLoading:achievementLoading,error:achievementError}=useGetAchievementsQuery(false);
    const {data:photoshoots,isLoading:photoshootLoading,error:photoShootError}=useGetPhotShootsQuery({page:0,size:6});
    const {data:clips,isLoading:clipsLoading,error:clipsError}=useGetUnseenClipsQuery({page:0,size:5});
    const navigate=useNavigate();
    return (
        <div className="">
            <HeroSection onNavigate={(path)=>navigate(path)}/>
            <FeatureVideosSection/>
            <GallerySection clip={{data:clips,error:clipsError,isLoading:clipsLoading}} photoshoot={{data:photoshoots,isLoading:photoshootLoading,error:photoShootError}}/>
            <AboutSection onNavigate={(path)=>navigate(path)}/>
            <EventsSection data={events} isLoading={eventsLoading} error={eventError} onNavigate={(id)=>navigate(`/events/${id}`)}/>
            <AchievementsSection data={achievements} error={achievementError} isLoading={achievementLoading}/>
        </div>
    );
}

export default Home;