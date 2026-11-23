import { motion } from "motion/react";
import { useGetUnseenClipsQuery } from "@/redux/api/clipsSlice";
import { useGetPhotShootsQuery } from "@/redux/api/photoshootSlice";
import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import ErrorFallback from "@/components/ErrorFallback";
import { Skeleton } from "@/components/ui/skeleton";
import {
  Empty,
  EmptyContent,
  EmptyDescription,
  EmptyHeader,
  EmptyMedia,
  EmptyTitle,
} from "@/components/ui/empty";
import { Button } from "@base-ui/react";
import { useState } from "react";
import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem, PaginationLink, PaginationNext, PaginationPrevious } from "@/components/ui/pagination";
import { getPagination } from "@/lib/utils";
import type { ClipsType, PhotoShootType } from "@/types/photoShoot";


const ShootCard = <T,>({
  photoshoot,
  type,
}: {
  photoshoot: T;
  type: "clips" | "photoshoots";
}) => {
  return (
    <motion.div
      key={photoshoot.id}
      whileHover={{ scale: 1.02 }}
      initial={{opacity:0,y:-20}}
      animate={{opacity:1,y:0}}
      transition={{duration:0.19}}
      className="relative group rounded-2xl overflow-hidden border border-border bg-background"
    >
      {type === "photoshoots" && (
        <img
          src={photoshoot.imagePath}
          className="w-full h-60 object-cover group-hover:scale-105 transition duration-500"
        />
      )}
      {type === "clips" && (
        <video
          src={photoshoot.videoUrl}
          controls
          className="w-full h-60 object-cover"
        />
      )}
    </motion.div>
  );
};

function PhotoShoots() {
    const [pageNumber,setPageNumber]=useState<number>(0) 
    const {
        data: photoshoots,
        error: photoshootError,
        isLoading: photoShootLoading,
    } = useGetPhotShootsQuery({ page: pageNumber, size: 10 });
    const {
        data: clips,
        error: clipError,
        isLoading: clipLoading,
    } = useGetUnseenClipsQuery({ page: pageNumber, size: 10 });
    const pages=getPagination(photoshoots?.number+1,photoshoots?.totalPages);
    if (photoshootError) return <ErrorFallback message={photoshootError.data.message} />;
    if (photoshootError) return <ErrorFallback message={clipError?.data.message} />;
    if (clips?.empty && photoshoots?.empty) {
        return (
        <div className="h-96 flex items-center justify-center">
            <Empty>
            <EmptyHeader>
                <EmptyMedia variant="icon">{/* <IconFolderCode /> */}</EmptyMedia>
                <EmptyTitle>No Projects Yet</EmptyTitle>
                <EmptyDescription>
                You haven&apos;t created any projects yet. Get started by creating
                your first project.
                </EmptyDescription>
            </EmptyHeader>
            <EmptyContent>
                <Button>Create Project</Button>
            </EmptyContent>
            </Empty>
        </div>
        );
    }
    return (
        <section className="max-w-7xl mx-auto px-6 py-10">
            <div className="mb-5">
                <h1 className="text-4xl font-bold mb-3">Media Gallery</h1>
                <p className="text-muted-foreground">
                Explore photoshoots, events, and unseen clips.
                </p>
            </div>
            <div className="grid sm:grid-cols-2 md:grid-cols-3 gap-6">
                {photoShootLoading
                ? Array.from({ length: 9 }).map((_, idx) => (
                    <Skeleton key={idx} className="h-32 w-full" />
                    ))
                : photoshoots?.content?.map((photoshoot: PhotoShootType, idx: number) => (
                    <ShootCard<PhotoShootType>
                        photoshoot={photoshoot}
                        type="photoshoots"
                        key={idx}
                    />
                    ))}
                {clipLoading
                ? Array.from({ length: 9 }).map((_, idx) => (
                    <Skeleton key={idx} className="h-50 w-full" />
                    ))
                : clips?.content?.map((clip: ClipsType, idx: number) => (
                    <ShootCard<ClipsType> photoshoot={clip} type="clips" key={idx} />
                    ))}
            </div>
            <Pagination>
                <PaginationContent>
                    <PaginationItem>
                        <PaginationPrevious onClick={()=>setPageNumber(pageNumber-1)}/>
                    </PaginationItem>
                    {pages.map((p,i)=>(
                        p==="..."?(<PaginationEllipsis key={i}/>):(
                        <PaginationItem key={i}>
                            <PaginationLink isActive={(photoshoots?.number+1===p)&&(clips?.number+1===p)} onClick={()=>setPageNumber(p as number)}>
                                {p}
                            </PaginationLink>
                        </PaginationItem>
                        )
                    ))}
                    <PaginationItem>
                        <PaginationNext onClick={()=>setPageNumber(pageNumber+1)}/>
                    </PaginationItem>
                </PaginationContent>
            </Pagination>
        </section>
    );    
}

export default PhotoShoots;
