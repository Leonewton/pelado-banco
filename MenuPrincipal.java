import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuPrincipal {

    private final Banco banco;
    private final Scanner scanner;
    private final List<Conta> contasCriadasPeloUsuario;

    public MenuPrincipal() {
        this.banco = new Banco();
        this.scanner = new Scanner(System.in).useLocale(Locale.US);
        this.contasCriadasPeloUsuario = new ArrayList<>();
    }

    public void executar() {
        System.out.println("Bem-vindo ao Pelado Banco!");
        boolean executando = true;
        while (executando) {
            mostrarMenu();
            String opcao = scanner.nextLine().trim();
            System.out.println();
            switch (opcao) {
                case "1":
                    cadastrarCliente();
                    break;
                case "2":
                    criarConta();
                    break;
                case "3":
                    listarContasSessao();
                    break;
                case "4":
                    listarClientes();
                    break;
                case "5":
                    listarContas();
                    break;
                case "0":
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        resumoFinal();
        scanner.close();
        System.out.println("Até logo!");
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("===== Menu =====");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Criar conta");
        System.out.println("3 - Listar contas criadas nesta sessão");
        System.out.println("4 - Listar todos os clientes");
        System.out.println("5 - Listar todas as contas");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void cadastrarCliente() {
        System.out.println("== Cadastrar cliente ==");
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("CPF (somente números): ");
        String cpf = scanner.nextLine().trim();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine().trim();
        banco.cadastrarCliente(nome, cpf, endereco);
    }

    private void criarConta() {
        System.out.println("== Criar conta ==");
        System.out.print("CPF do titular (já cadastrado): ");
        String cpf = scanner.nextLine().trim();

        System.out.print("Tipo de conta (corrente/poupanca): ");
        String tipo = scanner.nextLine().trim();

        Double saldoInicial = null;
        while (saldoInicial == null) {
            System.out.print("Saldo inicial (use ponto para decimais, ex: 1000.50): ");
            String saldoStr = scanner.nextLine().trim();
            try {
                saldoInicial = Double.parseDouble(saldoStr);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }

        Conta conta = banco.criarConta(cpf, tipo, saldoInicial);
        if (conta != null) {
            contasCriadasPeloUsuario.add(conta);
            System.out.println("Conta criada e adicionada à sua lista de criação: " + conta);
        }
    }

    private void listarContasSessao() {
        System.out.println("== Contas criadas nesta sessão ==");
        if (contasCriadasPeloUsuario.isEmpty()) {
            System.out.println("Nenhuma conta foi criada por você ainda.");
        } else {
            for (Conta c : contasCriadasPeloUsuario) {
                System.out.println(c);
            }
        }
    }

    private void listarClientes() {
        banco.listarClientes();
    }

    private void listarContas() {
        banco.listarContas();
    }

    private void resumoFinal() {
        System.out.println();
        System.out.println("Encerrando. Resumo das contas criadas por você nesta sessão:");
        if (contasCriadasPeloUsuario.isEmpty()) {
            System.out.println("Nenhuma conta foi criada.");
        } else {
            for (Conta c : contasCriadasPeloUsuario) {
                System.out.println(c);
            }
        }
    }
}
