import { useRef, useState } from "react"

export function EditarUsuarioForm() {

    const user = useRef()
    const password = useRef()
    const email = useRef()
    const telefono = useRef()
    const passwordConfirm = useRef()

    const [state, changeState] = useState("")

    function handleSubmit(evt) {
        evt.preventDefault()
        const isConfirmed = confirmPassword(password.current.value, passwordConfirm.current.value)

        const body = {
            'user': user.current.value,
            'password': password.current.value,
            'email': email.current.value,
            'telefono': telefono.current.value,
        }

        console.log(body)

        // console.log(user.current.value, password.current.value, passwordConfirm.current.value, email.current.value, telefono.current.value)
        console.log(isConfirmed)
    }

    function confirmPassword(password, passwordConfirm) {
        const result = password === passwordConfirm
        if (!result) {
            changeState("Las contraseñas no coinciden")
        } else {
            changeState("")
        }
        return result
    }



    /*
    Validaciones: 
        - nombre de usuario : not null, max 100
        - password: not null, min 8, max 100
        - email: not null
        - telephone: 
    */

    return (
        <>
            <form className='grid border-2 border-pink-300 p-5 rounded-md' onSubmit={handleSubmit} >
                <label className='text-lg' htmlFor="user"> Nombre de Usuario:</label>
                <input className="border-black border-2  rounded-sm mb-4" name="user" type="text" ref={user} required maxLength="100" value={sessionStorage.getItem("userName")} />
                <label className='text-lg' htmlFor="password"> Nueva Contraseña:</label>
                <input className="border-black border-2 mb-4 rounded-sm" name="password" type="password" ref={password} required minLength="8" maxLength="100" />
                <label className='text-lg' htmlFor="passwordConfirm">  Confirmar Nueva Contraseña:</label>
                <input className="border-black border-2 mb-4 rounded-sm" name="passwordConfirm" type="password" ref={passwordConfirm} required minLength="8" maxLength="100" />
                <label className='text-lg' htmlFor="email">   Email:</label>
                <input className="border-black border-2 mb-4 rounded-sm" name="email" type="email" ref={email} required value={sessionStorage.getItem("userEmail")} />
                <label className='text-lg' htmlFor="telefono">  Telefono:</label>
                <input className="border-black border-2 mb-4 rounded-sm" name="telefono" type="tel" ref={telefono} value={sessionStorage.getItem("userPhone")} />
                <p className="text-sm text-red-600" >{state}</p>
                <input className="border-black border-2 mb-4 cursor-pointer hover:bg-pink-200 hover:border-pink-200 duration-300 rounded-3xl" type="submit" value="Enviar" />
            </form>
        </>
    )
}
