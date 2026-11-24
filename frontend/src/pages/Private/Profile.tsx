import { Button } from "@/components/ui/button";
import { useGetCurrentUserQuery } from "@/redux/api/authSlice";
import UserBookingsSection from "@/components/private/UserBookingsSection";
import UserEventSection from "@/components/private/UserEventSection";
import Loader from "@/components/Loader";

export default function ProfilePage() {
    const {data:user,isLoading}=useGetCurrentUserQuery(false);
    if(isLoading)return<Loader/>;
    return (
        <section className="max-w-6xl mx-auto px-6 py-20 space-y-12">

        {/* PROFILE HEADER */}
        <div className="border border-border rounded-2xl p-6 bg-background flex flex-col md:flex-row gap-6 items-center md:items-start">

            {/* Initial Avatar */}
            <div className="w-20 h-20 rounded-full bg-foreground text-background flex items-center justify-center text-2xl font-bold">
            {user.username.charAt(0)}
            </div>

            {/* Info */}
            <div className="flex-1 text-center md:text-left">
            <h1 className="text-3xl font-bold">{user.username}</h1>
            <p className="text-muted-foreground">
                Creative community member & event participant
            </p>
            </div>

            <Button>Edit Profile</Button>
        </div>
        <UserBookingsSection/>
        <UserEventSection/>

        </section>
    );
}