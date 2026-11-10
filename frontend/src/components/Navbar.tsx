import { useState } from "react";
import { motion, AnimatePresence } from "motion/react";
import { Button } from "@/components/ui/button";
import { Menu, TriangleAlert, X } from "lucide-react";
import {Link, useNavigate} from "react-router";

export type NavbarLinkType={
    title: string,
    path: string;
}
interface UserProps{
    username?:string,
    role?:string,
}

interface Props{
    user:UserProps
    isError:boolean,
    isLoading:boolean
}

const Navbar=({user,isError,isLoading}:Props)=> {
    const [open, setOpen] = useState(false);
    const navigate=useNavigate();
    const links:NavbarLinkType[] = [
        {title:"Home", path:"/",},
        {title:"Community", path:"/posts",},
        {title:"Events", path:"/events",},
        {title:"Services", path:"/services",},
    ];
    
    return (
        <header className="sticky top-0 z-50 backdrop-blur-xl bg-background/70 border-b border-border">
            <div className="max-w-7xl mx-auto px-6 py-4 flex items-center justify-between">

                <div className="flex items-center gap-3">
                    <div className="w-9 h-9 rounded-xl bg-linear-to-tr from-purple-500 to-pink-500 flex items-center justify-center text-white font-bold">
                        VJS
                    </div>
                    <span className="text-lg font-semibold">VJSiddhu Vlogs</span>
                </div>

                {/* Desktop Nav */}
                <nav className="hidden md:flex items-center gap-8">
                    {links.map((link,idx) => (
                        <motion.div
                            key={idx}
                            whileHover={{ y: -2 }}
                            className="text-sm text-muted-foreground hover:text-foreground transition"
                        >
                            <Link to={link.path}>{link.title}</Link>
                        </motion.div>
                    ))}
                </nav>

                {isLoading?<div className="w-35 h-12 animate-pulse rounded-sm bg-accent"/>:isError?<div className="py-3 px-4 rounded-md text-md bg-red-400 text-white flex border-cyan-500 gap-2"><TriangleAlert size={22}/> Something wrong</div>:(
                    <div className="hidden md:flex items-center gap-3">
                        {user.username?<>
                            <Button variant="ghost" onClick={()=>navigate("/profile")}>Profile</Button>
                            <Button onClick={()=>navigate("/signout")}>Logout</Button>
                        </>:(
                            <>
                                <Button variant="ghost" onClick={()=>navigate("/signin")}>Login</Button>
                                <Button onClick={()=>navigate("/signup")}>Sign Up</Button>
                            </>
                        )}
                    </div>
                )}

                {/* Mobile Toggle */}
                <Button
                    onClick={() => setOpen(!open)}
                    className="md:hidden"
                >
                    {open ? <X /> : <Menu />}
                </Button>
            </div>

            {/* Mobile Menu */}
            <AnimatePresence>
                {open && (
                    <motion.div
                        initial={{ opacity: 0, y: -20 }}
                        animate={{ opacity: 1, y: 0 }}
                        exit={{ opacity: 0, y: -20 }}
                        className="md:hidden px-6 pb-6"
                    >
                        <div className="flex flex-col gap-4 mt-4">
                            {links.map((link,idx) => (
                                <div
                                    key={idx}
                                    className="text-muted-foreground hover:text-foreground"
                                >
                                    <Link to={link.path}>{link.title}</Link>
                                </div>
                            ))}

                            {user.username?<>
                                <Button variant="ghost" onClick={()=>navigate("/profile")}>Profile</Button>
                                <Button onClick={()=>navigate("/signout")}>Logout</Button>
                            </>:(
                                <>
                                    <Button variant="ghost" onClick={()=>navigate("/signin")}>Login</Button>
                                    <Button onClick={()=>navigate("/signup")}>Sign Up</Button>
                                </>
                            )}
                        </div>
                    </motion.div>
                )}
            </AnimatePresence>
        </header>
    )
}

export default Navbar;