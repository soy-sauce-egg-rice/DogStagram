import Image from 'next/image'
import styles from './page.module.css'
import Head from 'next/head'



export default function Home() {
  return (
    <div>
      <Head>
        <title> DogStagram</title>
        <meta name='description' content='Dogstagram for identify dog with Next.js'/>
        <link rel='icon' href='/favicon.ico'/>
      </Head>
      <main>
      this is Dogstagram. plz post Dogpicture. 
      </main>
    </div>
  )
}
