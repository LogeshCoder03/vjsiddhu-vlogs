import type { SerializedError } from "@reduxjs/toolkit";
import type { FetchBaseQueryError } from "@reduxjs/toolkit/query";

export interface AboutType {
    id: number;
    name: string;
    about: string;
    role: string;
    imagePath?: string
    instagram: string
}

export interface CreateContactType {
    name: string,
    message: string,
    email: string
}

export type ReduxError = FetchBaseQueryError | SerializedError | undefined

type Pageable = {
    offset: number, pageNumber: number, pageSize: number, paged: boolean,
    // sort: {…}, …
}

export interface PageParams{
    page:number,
    size:number
}

type Sort = {
    empty: boolean, sorted: boolean, unsorted: boolean
}

export interface PaginationType<T> {
    content: T,
    empty: boolean
    first: boolean
    last: boolean
    number: number
    numberOfElements: number,
    size: number,
    totalPages: number,
    totalElements: number,
    pageable: Pageable
    sort: Sort
}