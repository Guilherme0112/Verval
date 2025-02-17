"use client"

import style from "@/styles/Auth.module.css";
import { AnimatePresence, motion } from "framer-motion";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function Login() {
    const [message, setMessage] = useState("");
    const [isError, setIsError] = useState(false);
    const router = useRouter();

    async function onSubmit(event) {


        event.preventDefault();

        // Converte o objeto em JSON
        const formData = new FormData(event.target);
        const dataObject = {};
        formData.forEach((value, key) => {
            dataObject[key] = value;
        });

        // Faz a requisição do login para a API
        const response = await fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(dataObject)
        });

        const data = await response.json();
        

        if (!response.ok) {
            setMessage(data.message || "Ocorreu um erro. Tente novamente mais tarde");
            setIsError(true);
            return;
        }
    
        
        setMessage("");

        router.push("/dashboard");

    }
    return (
        <AnimatePresence>
            <motion.div
                key={router}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.1 }}>

                <main className="w-full h-screen flex justify-center items-center">
                    <form onSubmit={onSubmit} className={style.form}>
                        <div>
                            <label htmlFor="email" className={style.label}>Email</label>
                            <input type="email" id="email" className={style.input} name="email" />
                            <span className={style.underline}></span>
                        </div>
                        <div>
                            <label htmlFor="password" className={style.label}>Senha</label>
                            <input type="password" id="password" className={style.input} name="password" />
                            <span className={style.underline}></span>
                        </div>
                        {message && (
                            <div
                                className={"mt-4 text-center text-red-500"}
                                style={{ textShadow: "none" }}
                            >
                                {message}
                            </div>
                        )}
                        <a href="" className="w-full pl-9">Esqueci minha senha</a>
                        <button type="submit">Entrar</button>
                    </form>
                </main>
            </motion.div>
        </AnimatePresence>
    );
}