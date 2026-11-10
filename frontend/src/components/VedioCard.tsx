import {motion} from 'motion/react'
import {Play} from "lucide-react";

const VedioCard = ({video,i}:{video:any,i:number}) => {
    return (
        <motion.div
            key={i}
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ delay: i * 0.1 }}
            viewport={{ once: true }}
            className="group cursor-pointer"
        >

            <div className="relative overflow-hidden rounded-2xl">
                <img
                    src={video.image}
                    alt={video.title}
                    className="w-full h-60 object-cover group-hover:scale-105 transition duration-500"
                />

                <div className="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition" />

                {/* Play button */}
                <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition">
                    <div className="p-4 rounded-full bg-white/90 text-black">
                        <Play size={24} />
                    </div>
                </div>

                <span className="absolute bottom-2 right-2 text-xs bg-black/80 text-white px-2 py-1 rounded">
                    {video.duration}
                </span>
            </div>

            <div className="mt-4">
                <h3 className="font-semibold leading-snug line-clamp-2">
                    {video.title}
                </h3>
                <p className="text-sm text-muted-foreground mt-1">
                    {video.views}
                </p>
            </div>

        </motion.div>
    )
}

export default VedioCard