import image from "@/assets/1000033807.webp"
import { Link } from "react-router";
import VedioCard from "../VedioCard";

const videos = [
    {
        title: "Cinematic Photoshoot Behind the Scenes",
        views: "120K views",
        duration: "8:24",
        image: image,
    },
    {
        title: "Top 5 Camera Tricks You Must Know",
        views: "95K views",
        duration: "6:10",
        image: image,
    },
    {
        title: "Event Highlights 2025",
        views: "200K views",
        duration: "10:45",
        image: image,
    },
];

function FeatureVideosSection() {
    return (
        <section className="max-w-7xl mx-auto px-6 py-20">

            <div className="flex items-center justify-between mb-10">
                <h2 className="text-3xl md:text-4xl font-bold">
                    Featured Videos
                </h2>

                <Link to="/vedios" className="text-sm text-muted-foreground hover:text-foreground">
                    View All →
                </Link>
            </div>

            {/* Grid */}
            <div className="grid gap-8 sm:grid-cols-2 lg:grid-cols-3">
                {videos.map((video, i) => (
                    <VedioCard video={video} i={i} key={i}/>
                ))}
            </div>

        </section>

    );
}

export default FeatureVideosSection;