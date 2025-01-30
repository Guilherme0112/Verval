"use client"

import { AnimatePresence, motion } from "framer-motion";
import { usePathname } from "next/navigation";


export default function Dashboard(){
    const pathname = usePathname();

    return (
        <AnimatePresence mode="wait">
            <motion.div
                key={pathname}
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