// @ts-ignore
export enum EventStatus {
    Upcoming,
    Ongoing,
    Finished
}
export interface AddressType {
    city: string;
    district: string;
    state: string;
    pinCode: number;
}
export interface EventType {
    id: number
    name: string;
    description: string;
    username: string;
    logoPath: string;
    totalMembers: number;
    isLaunched: boolean;
    venue: AddressType;
    timing: Date;
    status: "Upcoming" |
    "Ongoing" |
    "Finished"
    createdAt: Date;
    launchedAt: Date;
}