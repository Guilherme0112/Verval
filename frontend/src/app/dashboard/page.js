"use client"

import { AnimatePresence, motion } from "framer-motion";
import { useRouter } from "next/navigation";


export default function Dashboard(){

    const router = useRouter()

    const token = localStorage.getItem("token");

    if(!token){
        router.push("/auth/login");
    }

    return (
        <AnimatePresence mode="wait">
            <motion.div
                key={router}
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                transition={{ duration: 0.1 }}
            >

                <h1>Hello World!</h1>

            </motion.div>
        </AnimatePresence>
    );
}