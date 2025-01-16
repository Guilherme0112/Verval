import style from '../styles/Components.module.css';

export default function Button1({ text, href }) {
    return (
        <a href={href} className={style.button1}>{text}</a>
    );
}