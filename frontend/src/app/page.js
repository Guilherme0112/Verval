"use client"

import BoxInfoHome from "@/components/BoxInfoHome";
import { AnimatePresence, motion } from "framer-motion";
import { usePathname } from "next/navigation";

export default function Home() {
  const pathname = usePathname(); // Acessa a rota atual
  return (
    <AnimatePresence mode="wait">
      <motion.div
        key={pathname}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
        transition={{ duration: 0.1 }}
      >
        <BoxInfoHome
          src="assets/logo_home.png"
          info="Seu site de gerenciamento de estoque!" >
        </BoxInfoHome>
      </motion.div>
    </AnimatePresence>
  );
}
