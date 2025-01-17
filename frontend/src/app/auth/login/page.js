"use client"

import style from "@/styles/Login.module.css";

export default function Login(){

    async function onSubmit(event){
        event.preventDefault();

        const formData = new FormData(event.target);
        const response = await fetch('http://localhost:8080/api/login', {
            method: 'POST',
            body: formData
        });

        const data = await response.json();
    }
    return (
        <main className="w-full h-screen flex justify-center items-center">
            <form onSubmit={onSubmit} className={style.form}>
                <div>
                    <label htmlFor="email" className={style.label}>Email</label>
                    <input type="email" id="email" className={style.input} name="email" required />
                    <span className={style.underline}></span>
                </div>
                <div>
                    <label htmlFor="password" className={style.label}>Senha</label>
                    <input type="password" id="password" className={style.input} name="password" required />
                    <span className={style.underline}></span>
                </div>
                <a href="" className="w-full pl-9">Esqueci minha senha</a>
                <button type="submit">Entrar</button>
            </form>
        </main>
    );
}