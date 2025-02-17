import { NextResponse } from "next/server";

export async function middleware(req) {


  const res = await fetch("http://localhost:8080/api/auth/validate", {
    method: "GET",
    credentials: "include",
  });


  if (!res.ok) {

    return NextResponse.redirect(new URL("/auth/login", req.url));

  } else {

    return NextResponse.next();
  }

}

export const config = {
  matcher: ["/dashboard"], // Garante que qualquer rota dentro de /dashboard seja protegida
};
