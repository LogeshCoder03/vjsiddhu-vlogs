import {z} from 'zod'

export const EventSchema=z.object({
    name:z.string().min(4),
    description:z.string().min(12),
    totalMembers:z.number().nonoptional(),
    timing:z.string().nonoptional(),
    logo:z.instanceof(File).optional(),
    venue:z.object({
        city:z.string().nonempty(),
        state:z.string().nonempty(),
        district:z.string().nonempty(),
        pinCode:z.number().min(6).nonoptional()
    })
})

export type EventType=z.infer<typeof EventSchema>;