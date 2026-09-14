package Jogo.Gamilist20.Servicos.Menus;

public class Menus {
    public void menuPrincipal() {
        System.out.println("""
                \n
                1. Procurar jogos
                2. Jogos favoritos
                3. Desenvolvedoras 
                4. Sair
                """);
    }
    public void menuJogosFavoritos() {
        System.out.println("""
                        \n
                        1. Adicionar jogo
                        2. Remover jogo
                        3. Listar favoritos
                        4. Redefinir status
                        5. Voltar ao menu principal
                        """);

    }
    public void menuDesenvolvedor() {
        System.out.println("""
                \n
                1. Lista de desenvolvedores
                2. Remover Desenvolvedor
                3. Todos jogos dessa desenvolvedora
                4. Voltar ao menu principal
                """);
    }

    public void menuStatus() {
        System.out.println("""
                \n
                Status do jogo:
                1. Vou jogar
                2. Jogando
                3. Concluido
                """);
    }
}
