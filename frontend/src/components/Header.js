import Link from "next/link";
import "../app/globals.css";

export default function Header(){
    return (
        <header>
            <div>
                <Link href="/">Início</Link>
            </div>
            <div>
                <Link href="/auth/login">Login</Link>
                <Link href="/auth/register">Criar Conta</Link>
            </div>
        </header>
    );
}