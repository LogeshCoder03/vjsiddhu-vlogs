import z from "zod";

export const postSchema=z.object({
    title:z.string().nonempty(),
    content:z.string().min(8),
    image:z.instanceof(File).optional()
})

export type PostSchemaType=z.infer<typeof postSchema>;