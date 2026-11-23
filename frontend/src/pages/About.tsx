import ErrorFallback from "@/components/ErrorFallback";
import Loader from "@/components/Loader";
import { useGetAboutsQuery } from "@/redux/api/aboutSlice";
import type { AboutType } from "@/types/comman";
import { IconBrandInstagramFilled } from "@tabler/icons-react";
import { motion } from "motion/react";

export default function AboutPage() {
    const {data:members,isLoading,error}=useGetAboutsQuery(false);
    if(isLoading)return<Loader/>;
    if(error)return<ErrorFallback message={error?.data?.message}/>;
    return (
        <section className="max-w-6xl mx-auto px-6 py-20 space-y-24">
        <div className="text-center">
            <h1 className="text-4xl font-bold mb-3">
            Meet Our Team
            </h1>
            <p className="text-muted-foreground">
            The creative minds behind our journey.
            </p>
        </div>
        {members.map((member:AboutType, idx:number) => (
            <motion.div
            key={member.id}
            initial={{ opacity: 0, y: 40 }}
            whileInView={{ opacity: 1, y: 0 }}
            viewport={{ once: true }}
            className={`grid md:grid-cols-2 gap-10 items-center ${
                idx % 2 === 1 ? "md:flex-row-reverse" : ""
            }`}
            >

            {/* Image */}
            <div className="overflow-hidden rounded-2xl">
                <img
                src={member.imagePath}
                className="w-full h-87.5 object-cover hover:scale-105 transition duration-500"
                />
            </div>

            {/* Content */}
            <div className="space-y-4">
                <h2 className="text-3xl font-bold">
                {member.name}
                </h2>

                <p className="text-muted-foreground">
                {member.role}
                </p>

                <p className="text-lg leading-relaxed">
                {member.about}
                </p>

                {/* Socials */}
                <div className="flex gap-4 pt-2 text-muted-foreground">
                    <IconBrandInstagramFilled className="cursor-pointer hover:text-foreground" />
                </div>
            </div>

            </motion.div>
        ))}

        </section>
    );
}