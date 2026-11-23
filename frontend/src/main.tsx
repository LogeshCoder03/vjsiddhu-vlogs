import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { createBrowserRouter, createRoutesFromElements, Route, RouterProvider } from 'react-router'
import Home from './pages/Home.tsx'
import { Provider } from 'react-redux'
import store from './redux/store.ts'

const router=createBrowserRouter(
    createRoutesFromElements(
        <Route element={<App/>} path="/">
            <Route element={<Home/>} index/>
            {/* <Route element={<About/>} path="/about"/>
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
                <Route element={<Contact/>} path="contact"/>
                <Route element={<Profile/>} path="profile"/>
            </Route>
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
