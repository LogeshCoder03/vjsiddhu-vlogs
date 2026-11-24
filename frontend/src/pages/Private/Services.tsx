import { useNavigate } from "react-router";
import second from '@/assets/1000033807.webp'
import {motion} from 'motion/react'

const services = [
  {
    title: "Photoshoots",
    desc: "Professional photoshoots for events, portraits, and more.",
    type: "photoshoot",
    image:second
  },
  {
    title: "Video Editing",
    desc: "Cinematic video editing for creators and brands.",
    type: "editing",
    image:second
  },
  {
    title: "Movie Audition",
    desc: "Join auditions and showcase your talent.",
    type: "audition",
    image:second
  },
];

const Services = () => {
  const navigate = useNavigate();

  return (
    <section className="max-w-7xl mx-auto px-6 py-20">

      <h1 className="text-4xl font-bold mb-10">
        Book a Service
      </h1>

      <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-8">
        {services.map((service, i) => (
          <motion.div
            key={i}
            whileHover={{ y: -5 }}
            className="cursor-pointer"
            onClick={() => navigate(`/booking/${service.type}`)}
          >

            {/* YOUR CARD COMPONENT */}
            <div className="border border-border rounded-2xl overflow-hidden bg-background">

              <img
                src={service.image}
                className="w-full h-48 object-cover"
              />

              <div className="p-5">
                <h3 className="font-semibold text-lg">
                  {service.title}
                </h3>
                <p className="text-sm text-muted-foreground mt-1">
                  {service.desc}
                </p>
              </div>

            </div>

          </motion.div>
        ))}
      </div>

    </section>
  )
}

export default Services

