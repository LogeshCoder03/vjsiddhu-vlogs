import {motion} from "motion/react";
import image from "@/assets/1000033807.webp"
import {Button} from "@/components/ui/button.tsx";
import {Users} from "lucide-react";

const AboutSection=({onNavigate}:{onNavigate:(path:string)=>void})=> {
    const highlights=["Vlogs","Movies","Comedy","Pets"]
    return (
        <section className="max-w-7xl mx-auto px-6 py-20 grid md:grid-cols-2 gap-12 items-center">

            {/* IMAGE */}
            <motion.div
                initial={{ opacity: 0, x: -40 }}
                whileInView={{ opacity: 1, x: 0 }}
                viewport={{ once: true }}
                className="relative"
            >
                {/* Glow */}
                <div className="absolute inset-0 -z-10 blur-3xl opacity-20 bg-linear-to-tr from-purple-500 to-pink-500 rounded-full"></div>

                <img
                    src={image}
                    alt="about"
                    className="rounded-2xl shadow-xl"
                />
            </motion.div>

            {/* CONTENT */}
            <motion.div
                initial={{ opacity: 0, x: 40 }}
                whileInView={{ opacity: 1, x: 0 }}
                viewport={{ once: true }}
            >
                {/* Heading */}
                <h2 className="text-3xl md:text-4xl font-bold mb-6">
                    About The Channel
                </h2>

                {/* Description */}
                <p className="text-muted-foreground mb-6 leading-relaxed">
                    This channel is dedicated to capturing creative moments through
                    photography, events, and storytelling. We bring together a passionate
                    community of creators who love to explore, learn, and grow.
                </p>

                {/* Highlights */}
                <div className="flex flex-wrap gap-4 mb-8">
                    {highlights.map((highlight,idx)=>(
                    <span className="px-4 py-2 text-sm rounded-full bg-accent text-accent-foreground" key={idx}>
                        {highlight}
                    </span>
                    ))}
                </div>

                {/* CTA */}
                <Button className="flex items-center gap-2 px-6 py-3" onClick={()=>onNavigate("/about")}>
                    <Users size={18} />
                    View All Members
                </Button>
            </motion.div>

        </section>

    );
}

export default AboutSection;