"use client"

export default function Login(){

    async function onSubmit(event){
        event.preventDefault();

        const formData = new FormData(event.target);
        const response = await fetch('http://localhost:8080/api/login', {
            method: 'POST',
            body: formData
        });

        const data = await response.json();
    }
    return (
        <form onSubmit={onSubmit}>
            <div>
                <label htmlFor="email">Email</label>
                <input type="email" id="email" name="email" required />
            </div>
            <div>
                <label htmlFor="password">Senha</label>
                <input type="password" id="password" name="password" required />
            </div>
            <button type="submit">Entrar</button>
        </form>
    );
}