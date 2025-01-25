"use client"

import BoxInfo from "@/components/BoxInfoHome";
import Info from "@/components/Info";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";

export default function ConfirmationEmail() {

    const params = useParams();
    const { token } = params;
    const [message, setMessage] = useState("");
    const [isError, setIsError] = useState(false);


    useEffect(() => {
        async function load() {

            try {

                const response = await fetch(`http://localhost:8080/api/confirmation_email/${token}`, {
                    method: "GET"
                });
                const data = await response.json();            

                if (!response.ok) {
                    setMessage(data.Erro)
                    return;
                }

                console.log("Resposta:" + data);

            } catch (err) {
                console.error("Erro ao confirmar e-mail: " + err);
            }
        }

        if (token) {
            load();
        }

    }, [token]);


    return (
        <Info
            mensagem={message || "Erro. Tente novamente mais tarde"}
        ></Info>
    );
}