import ErrorFallback from "@/components/ErrorFallback";
import Loader from "@/components/Loader";
import { useGetCurrentUserQuery } from "@/redux/api/authSlice";
import { Navigate, Outlet } from "react-router";

const AuthRouter = () => {
  const {
    data: user,
    error,
    isLoading,
    isError,
  } = useGetCurrentUserQuery(undefined);
  if (isLoading) return <Loader />;
  if (user?.username) return <Navigate to="/" />;
  if (isError) {
    if (typeof error?.status==="number") {
        return <ErrorFallback message={error?.data?.message} />;
    }
    return <ErrorFallback message={error?.error} />;
  }
  return (
    <section className="min-h-screen grid md:grid-cols-2">
      <div className="hidden md:flex items-center justify-center bg-muted p-10">
        <div className="max-w-md">
          <h1 className="text-5xl font-bold mb-4 text-shadow-amber-200 text-shadow-md">
            Welcome To
          </h1>
          <h1 className="mt-2 text-5xl font-bold mb-4 text-shadow-amber-200 text-shadow-md">
            VjSiddhu Vlogs
          </h1>

          <p className="text-muted-foreground">
            Join the community, explore events, and connect with creators.
          </p>
        </div>
      </div>
      <div className="flex items-center justify-center px-6 py-12">
        <Outlet />
      </div>
    </section>
  );
};

export default AuthRouter;
