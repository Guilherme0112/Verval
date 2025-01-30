import { NextResponse } from "next/server";

export function middleware(req){

    const token = req.headers.get("token");

    const rotasProtegidas = ["/dashboard"];

    if(!token && rotasProtegidas.some((route) => req.nextUrl.pathname.startsWith(route))){
        return NextResponse.redirect(new URL("/login", req.url));
    }

    return NextResponse.next();

}

export const config = {
    matcher: ["/dashboard/:path*"]
} 