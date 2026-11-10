import { motion } from "motion/react";
import {Link} from "react-router";
import type { NavbarLinkType } from "./Navbar";
import { IconBrandGithubFilled, IconBrandInstagramFilled, IconBrandTwitterFilled, IconBrandYoutubeFilled } from "@tabler/icons-react";

type SocialLink = {
    path: string,
    icon:React.ComponentType<any>,
}

const Footer=()=> {
    const links:NavbarLinkType[] = [
        {title:"Home", path:"/",},
        {title:"Community", path:"/posts",},
        {title:"Events", path:"/events",},
        {title:"PhotoShoots", path:"/photoshoots",},
    ];
    const socialMediaLinks:SocialLink[] = [
        {path:"/",icon:IconBrandGithubFilled,},
        {path:"/",icon:IconBrandInstagramFilled,},
        {path:"/",icon:IconBrandYoutubeFilled,},
        {path:"/",icon:IconBrandTwitterFilled,}
    ]
    return (
        <footer className="border-t border-border bg-background">
            <div className="max-w-7xl mx-auto px-6 py-12">

                {/* Top */}
                <div className="grid gap-10 md:grid-cols-3">

                    {/* Brand */}
                    <div>
                        <div className="flex items-center gap-3 mb-4">
                            <div className="w-9 h-9 rounded-xl bg-linear-to-tr from-purple-500 to-pink-500 flex items-center justify-center text-white font-bold">
                                L
                            </div>
                            <span className="font-semibold">VJSiddhu Vlogs</span>
                        </div>
                        <p className="text-sm text-muted-foreground">
                            Creating content, events and creative experiences.
                        </p>
                    </div>

                    {/* Links */}
                    <div className="flex flex-col gap-2">
                        <h4 className="font-semibold mb-2">Links</h4>
                        {links.map((link,idx) => (
                            <motion.div
                                key={idx}
                                whileHover={{ x: 4 }}
                                className="text-sm text-muted-foreground hover:text-foreground"
                            >
                                <Link to={link.path}>{link.title}</Link>
                            </motion.div>
                        ))}
                    </div>

                    {/* Social */}
                    <div>
                        <h4 className="font-semibold mb-3">Connect</h4>
                        <div className="flex gap-3">
                            {socialMediaLinks.map((link, idx) => (
                                <motion.div
                                    key={idx}
                                    whileHover={{ scale: 1.1 }}
                                    className="p-2 border border-border rounded-lg cursor-pointer"
                                >
                                    <Link to={link.path}><link.icon size={18} /></Link>
                                </motion.div>
                            ))}
                        </div>
                    </div>

                </div>

                {/* Bottom */}
                <div className="mt-10 pt-6 border-t border-border text-center text-sm text-muted-foreground">
                    © {new Date().getFullYear()} VJSiddhu Vlogs. All rights reserved.
                </div>

            </div>
        </footer>

    );
}

export default Footer;