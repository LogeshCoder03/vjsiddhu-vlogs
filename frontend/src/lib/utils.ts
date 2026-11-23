export { cn } from "cn"

export const getPagination=(currentPage:number,totalPages:number)=>{
    const pages:(number|string)[]=[];
    if (totalPages<=5) {
        return Array.from({length:totalPages},(_,i)=>i+1)
    }
    if(currentPage>2){
        pages.push(1);
        if (currentPage>3) {
            pages.push("...")
        }
    }
    const start=Math.max(1,currentPage-1);
    const end=Math.min(totalPages,currentPage+1)

    for (let index = start; index < end; index++) {
        pages.push(index);
    }
    if (currentPage<totalPages-1) {
        if(currentPage<totalPages-2)pages.push("...")
        pages.push(totalPages)
    }

    return pages;
}