import Link from 'next/link';
import style from '../styles/Components.module.css';

export default function Button1({ text, href }) {
    return (
        <Link href={href} className={style.button1}>{text}</Link>
    );
}