import { useGetCurrentUserQuery } from "@/redux/api/authSlice";
import {Navigate, Outlet} from "react-router";

function PrivateRouter() {
    const {isLoading,data:user}=useGetCurrentUserQuery(undefined);
    if (isLoading) return
    if (!user?.username) return <Navigate to="/signin"/>
    return (
        <div>
            <Outlet/>
        </div>
    );
}

export default PrivateRouter;