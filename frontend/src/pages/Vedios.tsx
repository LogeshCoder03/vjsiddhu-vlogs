import image from "@/asserts/Screenshot 2025-01-22 095942.png"
import VedioCard from "@/components/VedioCard";

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

const Vedios = () => {
    return (
      <div className="py-5">
            <h1 className="text-4xl font-bold px-4 py-5">Vedios Available on Youtube</h1>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-x-4 px-5 py-4">
                {videos.map((video,idx)=><VedioCard video={video} i={idx}/>)}
            </div>
      </div>
    )
}

export default Vedios