import { cookies } from "next/headers";
import { NextResponse } from "next/server";

export async function middleware(req) {
  
  
  function getCookie(name) {
    const value = `; ${cookies}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
    return null;
  }

  const token = getCookie('token');

  const res = await fetch("http://localhost:8080/api/auth/validate", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  });


  if (!res.ok) {
    return NextResponse.redirect(new URL("/auth/login", req.url))
  } else {
    return NextResponse.next();
  }

}

export const config = {
  matcher: ["/dashboard"], // Garante que qualquer rota dentro de /dashboard seja protegida
};
