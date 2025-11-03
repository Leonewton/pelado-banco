public class Main {
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Pelado Banco!");
        Banco PeladoBanco = new Banco();
        System.out.println();

        System.out.println("Cadastrando clientes");
        PeladoBanco.cadastrarCliente("João Silva", "12345678900", "Rua A, 123");
        System.out.println();
        PeladoBanco.cadastrarCliente("Maria Oliveira", "98765432100", "Avenida B, 456");
        System.out.println();

        System.out.println("Teste CPF duplicado:");
        PeladoBanco.cadastrarCliente("João Silva", "12345678900", "Rua A, 123");
        System.out.println();

        PeladoBanco.listarClientes();
        System.out.println();

        System.out.println("Criando contas para os clientes:");
        PeladoBanco.criarConta("12345678900", "corrente", 1000);
        System.out.println();
        PeladoBanco.criarConta("98765432100", "poupanca", 500);
        System.out.println();

        System.out.println("Teste CPF não cadastrado:");
        PeladoBanco.criarConta("11122233344", "corrente", 300);
        System.out.println();

        System.out.println("Teste tipo de conta inválido:");
        PeladoBanco.criarConta("12345678900", "investimento", 1000);
        System.out.println();

        PeladoBanco.listarContas();
        System.out.println();



    };


    
}
