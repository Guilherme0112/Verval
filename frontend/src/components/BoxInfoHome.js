import style from '../styles/Components.module.css';
import Button1 from './Button1';

export default function BoxInfo({ info, src }) {
    return (
        <div className={style.boxInfoHome}>
            <div>
                <img src={src} width="300px" className="rounded-xl mr-10" />
            </div>
            <div className="w-4/6 text-xl flex justify-end items-center flex-wrap">
                <div className="w-full">   
                    <h1 className="text-4xl">{info}</h1>
                </div>

                <div>
                    <Button1
                        text="Teste agora"
                        href=""
                    ></Button1>
                </div>
            </div>
        </div>
    );
}