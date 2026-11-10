import { Spinner } from './ui/spinner'

const Loader = () => {
  return (
    <div className='min-h-screen flex items-center justify-center'>
        <Spinner className='h-10 w-20 text-mauve-500'/>
    </div>
  )
}

export default Loader