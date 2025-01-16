import BoxInfoHome from "@/components/BoxInfoHome";
import Button1 from "@/components/Button1";
import Header from "@/components/Header";

export default function Home() {
  return (
    <>
      <Header></Header>

      <BoxInfoHome 
        src="assets/logo_home.png"
        info="Seu site de gerenciamento de estoque!" >
      </BoxInfoHome>
    </>
  );
}
