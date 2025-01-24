import style from "@/styles/Components.module.css";

export default function Info({ mensagem }){
    return (
        <div className={style.boxInfo}>
            <p>{mensagem}</p>
        </div>
    );
}