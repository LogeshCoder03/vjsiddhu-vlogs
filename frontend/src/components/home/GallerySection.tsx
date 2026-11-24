import image from "@/assets/1000033807.webp"
import {motion} from "motion/react"
import { Link } from "react-router";
import ErrorFallback from "@/components/ErrorFallback";
import { Skeleton } from "../ui/skeleton";
import type { HomePageProps } from "@/pages/Home";
import type { ClipsType, PhotoShootType } from "@/types/photoShoot";
import { Camera, Clock } from "lucide-react";
import { IconBrandInstagramFilled } from "@tabler/icons-react";
import type { PaginationType } from "@/types/comman";

function GallerySection({clip,photoshoot}:{clip:HomePageProps<PaginationType<ClipsType[]>>,photoshoot:HomePageProps<PaginationType<PhotoShootType[]>>}) {
    const {data:clips,error:clipsError,isLoading:clipsLoading}=clip;
    const {data:photoshoots,error:photoShootError,isLoading:photoshootLoading}=photoshoot;
    if(clipsError)return<ErrorFallback message={clipsError.error}/>
    if(clipsError)return<ErrorFallback message={photoShootError.error}/>
    if (clips?.empty&&photoshoots?.empty) {
        return 
    }
    return (
        <section className="max-w-7xl mx-auto px-6 py-20">
            {
                <div className="mb-12">
                    <div className="flex justify-between">
                        <h2 className="text-3xl md:text-4xl font-bold mb-3">
                            Photoshoots
                        </h2>
                        <Link to="/photoshoots" className="text-sm text-muted-foreground hover:text-foreground">
                            View All →
                        </Link>
                    </div>
                    <p className="text-muted-foreground">
                        Explore unseen moments, behind the scenes, and social highlights.
                    </p>
                </div>
            }
            {/* Grid Layout */}

            <div className="grid lg:grid-cols-3 gap-8">
                {!photoshoots?.empty&&(
                                    <motion.div
                    initial={{ opacity: 0, y: 30 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    viewport={{ once: true }}
                    className="space-y-4"
                >
                    <div className="flex items-center gap-2 font-semibold">
                        <Camera size={18} />
                        Unseen Shots
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        {photoshootLoading?(
                            Array.from({length:5}).map(
                                (_,idx)=><Skeleton className="w-full h-32" key={idx}/>
                            )
                        ):photoshoots.content?.map((photoshoot,i) => (
                            <div
                                key={i}
                                className="overflow-hidden rounded-xl"
                            >
                                <img
                                    src={image}
                                    className="w-full h-32 object-cover hover:scale-110 transition duration-500"
                                />
                            </div>
                        ))}
                    </div>
                </motion.div>
                )}
                {/* 2️⃣ Shoot Time */}
                {!clips?.empty&&(
                                    <motion.div
                    initial={{ opacity: 0, y: 30 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    transition={{ delay: 0.1 }}
                    viewport={{ once: true }}
                    className="space-y-4"
                >
                    <div className="flex items-center gap-2 font-semibold">
                        <Clock size={18} />
                        Shoot Moments
                    </div>

                    {clipsLoading?(
                        Array.from({length:5}).map((_,idx)=>(
                            <Skeleton key={idx}/>
                        ))
                    ):clips.content?.map((clip,i) => (
                        <div
                            key={i}
                            className="flex gap-4 items-center border border-border p-3 rounded-xl"
                        >
                            <img
                                src={image}
                                className="w-20 h-20 rounded-lg object-cover"
                            />
                            <div>
                                <p className="font-medium">Behind the scenes #{i}</p>
                                <p className="text-sm text-muted-foreground">
                                    Capturing creative moments
                                </p>
                            </div>
                        </div>
                    ))}
                </motion.div>
                )}

                {/* 3️⃣ Social Feed */}
                <motion.div
                    initial={{ opacity: 0, y: 30 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    transition={{ delay: 0.2 }}
                    viewport={{ once: true }}
                    className="space-y-4"
                >
                    <div className="flex items-center gap-2 font-semibold">
                        <IconBrandInstagramFilled size={18} />
                        Social Feed
                    </div>

                    <div className="grid grid-cols-3 gap-2">
                        {[1,2,3,4,5,6].map((i) => (
                            <div
                                key={i}
                                className="overflow-hidden rounded-lg"
                            >
                                <img
                                    src={image}
                                    className="w-full h-24 object-cover hover:scale-110 transition duration-500"
                                />
                            </div>
                        ))}
                    </div>
                </motion.div>
            </div>
        </section>

    );
}

export default GallerySection;