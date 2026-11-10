import {motion} from "motion/react";
import {BadgeCheck, PlayCircle} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";
import image from "@/asserts/Screenshot 2025-01-22 095942.png"

const HeroSection=({onNavigate}:{onNavigate:(path:string)=>void})=> {
    return (
        <section className="relative overflow-hidden">
            <div className="max-w-7xl mx-auto px-6 py-20 grid md:grid-cols-2 gap-12 items-center">
                <div>
                    <motion.div
                        initial={{ opacity: 0, y: 20 }}
                        animate={{ opacity: 1, y: 0 }}
                        className="flex items-center gap-2 mb-4 text-sm text-muted-foreground"
                    >
                        <BadgeCheck size={18} className="text-purple-500" />
                        Trusted by 100K+ creators
                    </motion.div>

                    <motion.h1
                        initial={{ opacity: 0, y: 30 }}
                        animate={{ opacity: 1, y: 0 }}
                        className="text-4xl md:text-6xl font-extrabold leading-tight mb-6"
                    >
                        Welcome To <br />
                        Vj Siddhu Vlogs.
                    </motion.h1>

                    <motion.p
                        initial={{ opacity: 0, y: 30 }}
                        animate={{ opacity: 1, y: 0 }}
                        className="text-muted-foreground text-lg mb-6"
                    >
                        Our channel take you into the details of my
                         life and give a glimpse of what inspires of what inspires me to do what i do.
                    </motion.p>

                    {/* Stats */}
                    <motion.div
                        initial={{ opacity: 0, y: 30 }}
                        animate={{ opacity: 1, y: 0 }}
                        className="flex gap-8 mb-8"
                    >
                        <div>
                            <p className="text-2xl font-bold">57M+</p>
                            <p className="text-sm text-muted-foreground">Subscribers</p>
                        </div>
                        <div>
                            <p className="text-2xl font-bold">500+</p>
                            <p className="text-sm text-muted-foreground">Vedios</p>
                        </div>
                        <div>
                            <p className="text-2xl font-bold">10+</p>
                            <p className="text-sm text-muted-foreground">Years Exp</p>
                        </div>
                    </motion.div>

                    <motion.div
                        initial={{ opacity: 0, y: 30 }}
                        animate={{ opacity: 1, y: 0 }}
                        className="flex gap-4"
                    >
                        <Button className="px-6 py-3 text-lg" onClick={()=>onNavigate("/contact")}>Contact</Button>

                        <Button variant="outline" 
                        className="px-6 py-3 text-lg flex items-center gap-2"
                        onClick={()=>onNavigate("/vedios")}
                        >
                            <PlayCircle size={18} />
                            Watch Videos
                        </Button>
                    </motion.div>
                </div>
                <motion.div
                    initial={{ opacity: 0, scale: 0.9 }}
                    animate={{ opacity: 1, scale: 1 }}
                    className="relative"
                >
                    <div className="absolute inset-0 -z-10 blur-3xl opacity-30 bg-linear-to-tr from-purple-500 to-pink-500 rounded-full"></div>

                    <img
                        src={image}
                        alt="hero"
                        className="rounded-2xl shadow-2xl"
                    />

                    <motion.div
                        initial={{ y: 20 }}
                        animate={{ y: 0 }}
                        transition={{ repeat: Infinity, duration: 3, repeatType: "reverse" }}
                        className="absolute bottom-6 left-6 bg-background/80 backdrop-blur-xl border border-border p-4 rounded-xl shadow-lg"
                    >
                        <p className="text-sm text-muted-foreground">New Achievement</p>
                        <p className="font-semibold">1M Views Reached 🎉</p>
                    </motion.div>
                </motion.div>

            </div>

            <div className="absolute top-0 left-0 w-full h-full -z-10 bg-[radial-gradient(circle_at_top,rgba(120,119,198,0.15),transparent_70%)]"></div>
        </section>

    );
}

export default HeroSection;