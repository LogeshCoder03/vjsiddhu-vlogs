import z from "zod";

export const BookingSchema=z.object({
    mobileNumber:z.number(),
    heldOn:z.string().nonempty(),
    address:z.object({
        city:z.string().nonempty(),
        state:z.string().nonempty(),
        district:z.string().nonempty(),
        pinCode:z.number().min(6).nonoptional()
    })
})

export type BookingType=z.infer<typeof BookingSchema>;