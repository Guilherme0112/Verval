import style from '../styles/Components.module.css';

export default function BoxInfo({ children }) {
    return (
        <div className={style.boxInfo}>
            {children}
        </div>
    );
}