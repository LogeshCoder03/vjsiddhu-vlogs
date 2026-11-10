import Navbar from "@/components/Navbar";
import Footer from "@/components/Footer";
import {Outlet} from "react-router";
import { useGetCurrentUserQuery } from "./redux/api/authSlice";
import { Toaster } from "./components/ui/toast";
export const fadeUp = {
    hidden: { opacity: 0, y: 30 },
    show: { opacity: 1, y: 0 }
};
const App = () => {
    const {data:user,isLoading,isError}=useGetCurrentUserQuery(false);
    return (
                <div className="relative min-h-screen bg-background text-foreground transition-colors duration-300">
            <div className="absolute inset-0 -z-10 bg-[radial-gradient(circle_at_top,rgba(120,119,198,0.15),transparent_70%)]">
                <div className="absolute top-0 left-1/2 -translate-x-1/2 w-150 h-150 bg-purple-500/20 blur-3xl rounded-full" />
                <div className="absolute bottom-0 right-0 w-125 h-125 bg-pink-500/20 blur-3xl rounded-full" />
            </div>
            <Navbar user={user} isError={isError} isLoading={isLoading}/>
            <div>
                <Outlet/>
            </div>
            <Footer />
            <Toaster />
        </div>
    )
}

export default App