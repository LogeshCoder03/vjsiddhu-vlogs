import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { createBrowserRouter, createRoutesFromElements, Route, RouterProvider } from 'react-router'
import Home from './pages/Home.tsx'
import { Provider } from 'react-redux'
import store from './redux/store.ts'
import AboutPage from './pages/About.tsx'
import PhotoShoots from './pages/PhotoShoots.tsx'
import Posts from './pages/Posts.tsx'
import Vedios from './pages/Vedios.tsx'
import AuthRouter from './pages/auth/AuthRouter.tsx'
import Login from './pages/auth/Login.tsx'
import Register from './pages/auth/Register.tsx'
import ForgotPassword from './pages/auth/ForgotPassword.tsx'
import ResetPassword from './pages/auth/ResetPassword.tsx'
import PrivateRouter from './pages/Private/PrivateRouter.tsx'
import Events from './pages/Events.tsx'
import Services from './pages/Private/Services.tsx'
import EventDetails from './pages/Private/EventDetails.tsx'
import PostDetail from './pages/Private/Comments.tsx'
import Logout from './pages/Private/Logout.tsx'
import Booking from './pages/Private/Booking.tsx'

const router=createBrowserRouter(
    createRoutesFromElements(
        <Route element={<App/>} path="/">
            <Route element={<Home/>} index/>
            <Route element={<AboutPage/>} path="/about"/>
            <Route element={<PhotoShoots/>} path="/photoshoots"/>
            <Route element={<Posts/>} path="/posts"/>
            <Route element={<PostDetail/>} path="/posts/:id"/>
            <Route element={<Vedios/>} path="/vedios"/>
            <Route element={<EventDetails/>} path="/events/:id"/>
            <Route element={<Services/>} path="/services"/>
            <Route element={<AuthRouter/>}>
                <Route element={<Login/>} path="/signin"/>
                <Route element={<Register/>} path="/signup"/>
                <Route element={<ForgotPassword/>} path="/forgot-password"/>
                <Route element={<ResetPassword/>} path="/reset-password"/>
            </Route>
            <Route element={<PrivateRouter/>} path="">
                <Route path='/signout' element={<Logout/>}/>
                <Route element={<Events/>} path="events"/>
                <Route element={<Booking/>} path="/booking/:type"/>
                {/* <Route element={<Contact/>} path="contact"/> */}
                {/* <Route element={<Profile/>} path="profile"/> */}
            </Route>
            {/* 
            <Route element={<AdminRouter/>} path="/admin">
                <Route element={<PublishEvents/>} path='publisher'/>
                <Route element={<AdminPage/>} path=':page'/>
            </Route> */}
        </Route>
    )
)

createRoot(document.getElementById('root')!).render(
        <Provider store={store}>
            <RouterProvider router={router}/>
        </Provider>

)
