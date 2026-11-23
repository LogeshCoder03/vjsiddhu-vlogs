import {
  useCreateEventMutation,
  useDeleteEventMutation,
  useGetEventsQuery,
  useUpdateEventMutation,
} from "@/redux/api/eventsSlice";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { EventSchema, type EventType } from "@/validations/event";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogDescription,
  DialogTitle,
  DialogClose,
  DialogHeader,
} from "@/components/ui/dialog";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import { Calendar, MapPin, Plus } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Button } from "@/components/ui/button";
import { motion } from "motion/react";
import { Spinner } from "@/components/ui/spinner";
import { useState } from "react";
import { useGetCurrentUserQuery } from "@/redux/api/authSlice";
import ErrorFallback from "@/components/ErrorFallback";
import Loader from "@/components/Loader";
import { useNavigate } from "react-router";
import {
  Empty,
  EmptyDescription,
  EmptyHeader,
  EmptyMedia,
  EmptyTitle,
} from "@/components/ui/empty";
import { getPagination } from "@/lib/utils";
import { EventSkeleton } from "@/components/home/EventsSection";
import { toast } from "@/components/ui/toast";

function Events() {
  const navigate = useNavigate();
  const [state, setState] = useState<"create" | "update" | null>(null);
  const [pageNumber, setPageNumber] = useState<number>(1);
  const [id, setId] = useState();
  const [open, onOpen] = useState<{ delete: boolean; create: boolean }>({
    delete: false,
    create: false,
  });
  const [createEvent, { isLoading: createLoading }] = useCreateEventMutation();
  const [updateEvent, { isLoading: updateLoading }] = useUpdateEventMutation();
  const [deleteEvent, { isLoading: deleteLoading }] = useDeleteEventMutation();
  const {
    data: events,
    isLoading,
    error,
  } = useGetEventsQuery({ page: pageNumber - 1, size: 10 });
  const { data: user, isLoading: userLoading } = useGetCurrentUserQuery(false);
  const isAdmin = false;
  const {
    handleSubmit,
    register,
    formState: { errors, isValid },
    trigger,
    setValue,
    reset,
  } = useForm({
    resolver: zodResolver(EventSchema),
    mode: "onChange",
  });

  const handleFormData = (payload: EventType): FormData => {
    const formData = new FormData();
    const fields = Object.entries(payload);
    fields.forEach(([key, value]) => {
      if (key.startsWith("venue")) {
        Object.entries(value).forEach(([subKey, subValue]) => {
          formData.append(`${key}.${subKey}`, subValue);
        });
      } else {
        formData.append(key, value as string);
      }
    });
    return formData;
  };
  const handleCreate = async (payload: EventType) => {
    const isValid = await trigger();
    if (!isValid) return toast.add({ title: "Invalid data", type: "error" });
    const formData = handleFormData(payload);
    if (state == "create") {
      const response = await createEvent(formData).unwrap();
      toast.add({
        title: response.message,
        type: "success",
      });
    } else if (state == "update") {
      const response = await updateEvent({ formData, id: id }).unwrap();
      toast.add({
        title: response.message,
        type: "success",
      });
    }
    reset();
    onOpen({ create: false, delete: false });
  };
  const pages = getPagination(events?.number + 1, events?.totalPages);
  if (userLoading) return <Loader />;
  if (error) return <ErrorFallback message={error?.data?.message} />;
  return (
    <section className="max-w-7xl mx-auto px-6">
      <div className="flex justify-end mt-10">
        {isAdmin && (
          <Button
            onClick={() => {
              reset();
              setState("create");
              onOpen({ create: true, delete: false });
            }}
          >
            <Plus size={18} />
            Create Event
          </Button>
        )}
      </div>
      <div className="mb-12">
        <h1 className="text-4xl font-bold mb-3">All Events</h1>
        <p className="text-muted-foreground">
          Discover, join, and explore all events.
        </p>
      </div>
      {/* Grid */}
      {events?.empty ? (
        <Empty>
          <EmptyHeader>
            <EmptyMedia variant="icon">{/* <IconFolderCode /> */}</EmptyMedia>
            <EmptyTitle>No Projects Yet</EmptyTitle>
            <EmptyDescription>
              You haven&apos;t created any projects yet. Get started by creating
              your first project.
            </EmptyDescription>
          </EmptyHeader>
        </Empty>
      ) : (
        <div className="">
          <div className="grid gap-8 sm:grid-cols-2 lg:grid-cols-3">
            {isLoading
              ? Array.from({ length: 3 }).map((_, idx) => (
                  <EventSkeleton key={idx} />
                ))
              : events?.content?.map((event: any, i: number) => (
                  <motion.div
                    key={event.id}
                    initial={{ opacity: 0, y: 30 }}
                    whileInView={{ opacity: 1, y: 0 }}
                    transition={{ delay: i * 0.1 }}
                    viewport={{ once: true }}
                    className="group border border-border rounded-2xl overflow-hidden bg-background"
                  >
                    {/* Image */}
                    <div className="relative overflow-hidden">
                      <img
                        src={event.logo_path}
                        className="w-full h-56 object-cover group-hover:scale-105 transition duration-500"
                      />
                      <span
                        className={`absolute top-3 left-3 text-xs px-3 py-1 rounded-full 
                                ${
                                  event.status === "Ongoing"
                                    ? "bg-green-500 text-white"
                                    : event.status === "Upcoming"
                                      ? "bg-blue-500 text-white"
                                      : "bg-muted text-foreground"
                                }`}
                      >
                        {event.status}
                      </span>
                    </div>

                    {/* Content */}
                    <div className="p-5 space-y-3">
                      <h3 className="font-semibold text-lg">{event.name}</h3>

                      <div className="text-sm text-muted-foreground space-y-2">
                        <div className="flex items-center gap-2">
                          <Calendar size={16} />
                          {new Date(event.timing).toDateString()}
                        </div>
                        <div className="flex items-center gap-2">
                          <MapPin size={16} />
                          {event.venue.city}
                        </div>
                      </div>

                      {/* CTA */}
                      {isAdmin ? (
                        <div className="flex items-center justify-center gap-3 pt-3">
                          <Button
                            onClick={() => navigate(`/events/${event.id}`)}
                          >
                            {event.status === "Ongoing"
                              ? "Join Now"
                              : event.status === "Upcoming"
                                ? "View Details"
                                : "View Recap"}
                          </Button>
                          <Button
                            className="bg-blue-400"
                            onClick={() => {
                              onOpen({ create: true, delete: false });
                              reset(event);
                              setId(event.id);
                              setState("update");
                            }}
                          >
                            Update
                          </Button>
                          <Dialog
                            open={open.delete}
                            onOpenChange={() =>
                              onOpen({ delete: !open.delete, create: false })
                            }
                          >
                            <DialogTrigger className="bg-red-300 text-white rounded-md px-2 py-1">
                              Delete
                            </DialogTrigger>
                            <DialogContent>
                              <DialogHeader>
                                <DialogTitle>Delete</DialogTitle>
                              </DialogHeader>
                              <div className="">
                                <Button
                                  variant="destructive"
                                  onClick={() => deleteEvent(event.id)}
                                >
                                  {deleteLoading ? (
                                    <>
                                      <Spinner />
                                      Deleting...
                                    </>
                                  ) : (
                                    "Delete"
                                  )}
                                </Button>
                                <DialogClose>Cancel</DialogClose>
                              </div>
                            </DialogContent>
                          </Dialog>
                        </div>
                      ) : (
                        <Button className="w-full mt-3">
                          {event.status === "Live"
                            ? "Join Now"
                            : event.status === "Upcoming"
                              ? "View Details"
                              : "View Recap"}
                        </Button>
                      )}
                    </div>
                  </motion.div>
                ))}
          </div>
          <div className="">
            <Pagination>
              <PaginationContent>
                <PaginationItem>
                  <PaginationPrevious
                    isActive={events?.first}
                    onClick={() => setPageNumber(pageNumber - 1)}
                  />
                </PaginationItem>
                {pages.map((p, i) =>
                  p === "..." ? (
                    <PaginationEllipsis key={i} />
                  ) : (
                    <PaginationItem key={i}>
                      <PaginationLink
                        isActive={events?.number === p}
                        onClick={() => setPageNumber(p as number)}
                      >
                        {p}
                      </PaginationLink>
                    </PaginationItem>
                  ),
                )}
                <PaginationItem>
                  <PaginationNext
                    onClick={() => setPageNumber(pageNumber + 1)}
                  />
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          </div>
        </div>
      )}
      <Dialog
        open={open.create}
        onOpenChange={() => onOpen({ delete: false, create: !open.create })}
      >
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Create Event</DialogTitle>
            <DialogDescription>Create Event</DialogDescription>
          </DialogHeader>
          <form
            className="space-y-4 mt-4"
            onSubmit={handleSubmit(handleCreate)}
          >
            <Input placeholder="Event Name" {...register("name")} />
            {errors.name && (
              <p className="text-destructive">{errors.name.message}</p>
            )}
            <Textarea
              placeholder="Event description"
              {...register("description")}
            />
            {errors.description && (
              <p className="text-destructive">{errors.description.message}</p>
            )}
            <div className="grid grid-cols-1 md:grid-cols-2 md:gap-2">
              <Input
                placeholder="Total Members"
                {...register("totalMembers", { valueAsNumber: true })}
              />
              {errors.totalMembers && (
                <p className="text-destructive">
                  {errors.totalMembers.message}
                </p>
              )}
              <Input
                type="datetime-local"
                onChange={(e) => setValue("timing", e.target.value + ":00")}
              />
              {errors.timing && (
                <p className="text-destructive">{errors.timing.message}</p>
              )}
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 md:gap-2">
              <Input placeholder="City" {...register("venue.city")} />
              {errors.venue?.city && (
                <p className="text-destructive">{errors.venue.city?.message}</p>
              )}
              <Input placeholder="State" {...register("venue.state")} />
              {errors.venue?.state && (
                <p className="text-destructive">
                  {errors.venue.state?.message}
                </p>
              )}
              <Input placeholder="District" {...register("venue.district")} />
              {errors.venue?.district && (
                <p className="text-destructive">
                  {errors.venue.district?.message}
                </p>
              )}
              <Input
                placeholder="Pin/Zip Code"
                {...register("venue.pinCode", { valueAsNumber: true })}
              />
              {errors.venue?.pinCode && (
                <p className="text-destructive">
                  {errors.venue.pinCode?.message}
                </p>
              )}
              <Input
                type="file"
                name="logo"
                onChange={(e) => setValue("logo", e.target?.files?.[0])}
              />
              {errors.logo && (
                <p className="text-destructive">{errors.logo?.message}</p>
              )}
            </div>
            <div className="flex justify-end gap-2">
              <Button type="submit" disabled={!isValid}>
                {state === "create" ? (
                  createLoading ? (
                    <>
                      <Spinner />
                      Creating...
                    </>
                  ) : (
                    "Create"
                  )
                ) : updateLoading ? (
                  <>
                    <Spinner />
                    Updating...
                  </>
                ) : (
                  "Update"
                )}
              </Button>
              <DialogClose>Close</DialogClose>
            </div>
          </form>
        </DialogContent>
      </Dialog>
    </section>
  );
}

export default Events;
