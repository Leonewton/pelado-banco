package src.view;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import src.model.Conta;
import src.service.Banco;

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
                case "6":
                    fazerDeposito();
                    break;
                case "7":
                    fazerSaque();
                    break;
                case "8":
                    fazerTransferencia();
                    break;
                case "9":
                    consultarSaldo();
                    break;
                case "10":
                    exibirEstatisticasBanco();
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
        System.out.println("6 - Depósitar");
        System.out.println("7 - Sacar");
        System.out.println("8 - Transferir");
        System.out.println("9 - Consultar saldo");
        System.out.println("10 - Exibir estatísticas do banco");
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

    private void fazerDeposito() {
        System.out.println("== Depósito ==");
        System.out.print("Número da conta: ");
        String numeroConta = scanner.nextLine().trim();
        Conta conta = banco.buscarContaPorNumero(numeroConta);
        if (conta == null) {
            System.out.println("Conta não encontrada.");
            return;
        }

        Double valor = null;
        while (valor == null) {
            System.out.print("Valor do depósito: ");
            String valorStr = scanner.nextLine().trim();
            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }

        conta.depositar(valor);
    }

    private void fazerSaque() {
        System.out.println("== Saque ==");
        System.out.print("Número da conta: ");
        String numeroConta = scanner.nextLine().trim();
        Conta conta = banco.buscarContaPorNumero(numeroConta);
        if (conta == null) {
            System.out.println("Conta não encontrada.");
            return;
        }

        Double valor = null;
        while (valor == null) {
            System.out.print("Valor do saque: ");
            String valorStr = scanner.nextLine().trim();
            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }

        boolean sucesso = conta.sacar(valor);
        if (sucesso) {
            System.out.println("Saque realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente para o saque."); 
        }
    }

    private void fazerTransferencia() {
        System.out.println("== Transferência ==");
        System.out.print("Número da conta de origem: ");
        String numeroContaOrigem = scanner.nextLine().trim();
        Conta contaOrigem = banco.buscarContaPorNumero(numeroContaOrigem);
        if (contaOrigem == null) {
            System.out.println("Conta de origem não encontrada.");
            return;
        }

        System.out.print("Número da conta de destino: ");
        String numeroContaDestino = scanner.nextLine().trim();
        Conta contaDestino = banco.buscarContaPorNumero(numeroContaDestino);
        if (contaDestino == null) {
            System.out.println("Conta de destino não encontrada.");
            return;
        }

        Double valor = null;
        while (valor == null) {
            System.out.print("Valor da transferência: ");
            String valorStr = scanner.nextLine().trim();
            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }

        banco.fazerTransferencia(numeroContaOrigem, numeroContaDestino, valor);
    }

    private void consultarSaldo() {
        System.out.println("== Consultar saldo ==");
        System.out.print("Número da conta: ");
        String numeroConta = scanner.nextLine().trim();
        banco.consultarSaldo(numeroConta);
    }

    private void exibirEstatisticasBanco() {
        banco.exibirEstatisticasBanco();
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
