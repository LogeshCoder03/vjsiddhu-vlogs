import { Trophy, Star, Award } from "lucide-react";
import {motion} from "motion/react";
import { Skeleton } from "../ui/skeleton";
import type { HomePageProps } from "@/pages/Home";
import type {Achivement} from '@/types/achievement'
import ErrorFallback from "../ErrorFallback";

const icons={
    trophy:Trophy,
    star:Star,
    award:Award
}

const AchievementsSection = ({data:achievements,error,isLoading}:HomePageProps<Achivement>) => {
    const stats=[
        {value:"57M+",label:"Subscribers"},
        { value: "500+", label: "Projects" },
        { value: "50+", label: "Awards" },
        { value: "10+", label: "Years Experience" },
    ]
    return (
        <section className="max-w-7xl mx-auto px-6 py-20">

            {/* Heading */}
            <div className="text-center mb-12">
                <h2 className="text-3xl md:text-4xl font-bold mb-3">
                    Achievements
                </h2>
                <p className="text-muted-foreground">
                    Milestones and recognition earned through passion and creativity.
                </p>
            </div>

            {/* Stats */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-6 mb-16">
                {stats.map((stat, i) => (
                    <motion.div
                        key={i}
                        initial={{ opacity: 0, y: 30 }}
                        whileInView={{ opacity: 1, y: 0 }}
                        transition={{ delay: i * 0.1 }}
                        viewport={{ once: true }}
                        className="text-center border border-border rounded-2xl p-6"
                    >
                        <p className="text-2xl md:text-3xl font-bold">
                            {stat.value}
                        </p>
                        <p className="text-sm text-muted-foreground mt-1">
                            {stat.label}
                        </p>
                    </motion.div>
                ))}
            </div>

            {/* Achievement Cards */}
            <div className="grid gap-8 md:grid-cols-3">
                {error?(<div className="col-span-4"><ErrorFallback message={error.error}/></div>):isLoading?(
                    Array.from({length:3}).map((_,idx)=>(
                        <Skeleton key={idx} className="w-full max-w-md h-40 px-4 py-5 space-y-4">
                            <Skeleton className="mb-4 p-3 w-fit rounded-xl bg-foreground/10 size-10"/>
                            <Skeleton className="h-4 w-60 bg-foreground/10"/>
                            <Skeleton className="h-6 w-60 bg-foreground/10"/>
                        </Skeleton>
                    ))
                ):(achievements?.content?.map((achievement, i) => {
                    // @ts-ignore
                    const Icon = icons[achievement.type.toLowerCase()];

                    return (
                        <motion.div
                            key={i}
                            initial={{ opacity: 0, y: 30 }}
                            whileInView={{ opacity: 1, y: 0 }}
                            transition={{ delay: i * 0.1 }}
                            viewport={{ once: true }}
                            className="group border border-border rounded-2xl p-6 hover:shadow-lg transition"
                        >
                            <div className="mb-4 p-3 w-fit rounded-xl bg-accent">
                                <Icon size={22} />
                            </div>

                            <h3 className="font-semibold text-lg mb-2">
                                {achievement.title}
                            </h3>

                            <p className="text-sm text-muted-foreground">
                                {achievement.description}
                            </p>
                        </motion.div>
                    );
                }))}
            </div>

        </section>

    );
};

export default AchievementsSection;