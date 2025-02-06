import { NextResponse } from "next/server";

export function middleware(req) {
  const token = req.cookies.get("token")?.value; // Corrigindo a forma de acessar o cookie

  if (!token) {
    return NextResponse.redirect(new URL("/auth/login", req.url));
  }

  return NextResponse.next();
}

// Aplicando middleware apenas para páginas protegidas
export const config = {
  matcher: ["/dashboard"], // Garante que qualquer rota dentro de /dashboard seja protegida
};
