"use client"

import style from "@/styles/Auth.module.css";
import { AnimatePresence, motion } from "framer-motion";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function Register() {

    const router = useRouter();
    const [messages, setMessages] = useState("");
    const [isError, setIsError] = useState(false);

    const [erroNome, setErroNome] = useState("");
    const [erroEmail, setErroEmail] = useState("");
    const [erroSenha, setErroSenha] = useState("");
    const [erroRSenha, setErroRSenha] = useState("");

    async function onSubmit(event) {
        event.preventDefault();

        //Transforma o objeto em array com o _usuario para o BindingResult validar
        const formData = new FormData(event.target);
        const dataObject = {};
        formData.forEach((value, key) => {
            dataObject[key] = value;
        });

        // console.log(dataObject);

        const response = await fetch("http://localhost:8080/api/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dataObject)
        });

        const data = await response.json();

        console.log(data)

        if (!response.ok) {
            setMessages(data.details || "Ocorreu um erro");
            setIsError(true);
            return;
        }

        router.push("/mails/confirmation_email");
    }
    return (
        <AnimatePresence>
            <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.1 }}>

                <main className="w-full h-screen flex justify-center items-center">
                    <form onSubmit={onSubmit} className={style.form}>
                        <div>
                            <label htmlFor="nome" className={style.label}>Nome</label>
                            <input type="text" id="nome" className={style.input} name="nome" />
                            <span className={style.underline}></span>
                        </div>
                        <div>
                            <label htmlFor="email" className={style.label}>Email</label>
                            <input type="email" id="email" className={style.input} name="email" />
                            <span className={style.underline}></span>
                        </div>
                        <div>
                            <label htmlFor="password" className={style.label}>Senha</label>
                            <input type="password" id="password" className={style.input} name="senha" />
                            <span className={style.underline}></span>
                        </div>
                        <div>
                            <label htmlFor="rpassword" className={style.label}>Repita a Senha</label>
                            <input type="password" id="rpassword" className={style.input} />
                            <span className={style.underline}></span>
                        </div>
                        <button type="submit">Criar Conta</button>
                    </form>
                </main>
            </motion.div>
        </AnimatePresence>
    );
}