package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.Cliente;
import model.Conta;
import model.ContaPoupanca;
import service.Banco;
import service.EstatisticasBanco;

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
                    aplicarRendimentosPoupanca();
                    break;
                case "11":
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
        System.out.println("10 - Aplicar rendimentos em contas poupança");
        System.out.println("11 - Exibir estatísticas do banco");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void cadastrarCliente() {
        System.out.println("== Cadastrar cliente ==");
        String nome;
        do {
            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Nome não pode ficar vazio. Tente novamente.");
            }
        } while (nome.isEmpty());

        String cpf;
        do {
            System.out.print("CPF (somente números): ");
            cpf = scanner.nextLine().trim();
            if (cpf.isEmpty()) {
                System.out.println("CPF não pode ficar vazio. Tente novamente.");
                continue;
            }
            if (!cpf.matches("\\d+")) {
                System.out.println("CPF inválido. Use apenas números. Tente novamente.");
                cpf = "";
            }
        } while (cpf.isEmpty());

        String endereco;
        do {
            System.out.print("Endereço: ");
            endereco = scanner.nextLine().trim();
            if (endereco.isEmpty()) {
                System.out.println("Endereço não pode ficar vazio. Tente novamente.");
            }
        } while (endereco.isEmpty());

        Cliente novo = banco.cadastrarCliente(nome, cpf, endereco);
        if (novo == null) {
            System.out.println("Erro: Cliente com CPF " + cpf + " já cadastrado.");
        } else {
            System.out.println("Cliente cadastrado com sucesso: " + novo);
        }
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
        } else {
            if (!banco.clienteExiste(cpf)) {
                System.out.println("Erro: Cliente com CPF " + cpf + " não encontrado.");
            } else {
                System.out.println("Erro: Tipo de conta inválido. Use 'corrente' ou 'poupanca'.");
            }
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
        List<Cliente> clientes = banco.listarClientesData();
        System.out.println("Lista de Clientes:");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
    }

    private void listarContas() {
        List<Conta> contas = banco.listarContasData();
        System.out.println("Lista de Contas:");
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            for (Conta c : contas) {
                System.out.println(c);
            }
        }
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

        boolean sucesso = banco.fazerDepositoData(numeroConta, valor);
        if (sucesso) {
            System.out.println("Depósito de R$" + valor + " realizado na conta " + numeroConta);
            System.out.println("Novo saldo: R$" + banco.consultarSaldoData(numeroConta));
        } else {
            System.out.println("Erro ao realizar depósito.");
        }
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

        boolean sucesso = banco.fazerSaqueData(numeroConta, valor);
        if (sucesso) {
            System.out.println("Saque realizado com sucesso.");
            System.out.println("Novo saldo: R$" + banco.consultarSaldoData(numeroConta));
        } else {
            System.out.println("Saldo insuficiente para o saque ou conta não encontrada.");
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

        boolean sucesso = banco.fazerTransferenciaData(numeroContaOrigem, numeroContaDestino, valor);
        if (sucesso) {
            System.out.println("Transferência de R$" + valor + " realizada de " + numeroContaOrigem + " para " + numeroContaDestino);
            System.out.println("Novo saldo da conta " + numeroContaOrigem + ": R$" + banco.consultarSaldoData(numeroContaOrigem));
            System.out.println("Novo saldo da conta " + numeroContaDestino + ": R$" + banco.consultarSaldoData(numeroContaDestino));
        } else {
            System.out.println("Erro: transferência falhou (conta não encontrada ou saldo insuficiente).");
        }
    }

    private void consultarSaldo() {
        System.out.println("== Consultar saldo ==");
        System.out.print("Número da conta: ");
        String numeroConta = scanner.nextLine().trim();
        Double saldo = banco.consultarSaldoData(numeroConta);
        if (saldo == null) {
            System.out.println("Erro: Conta " + numeroConta + " não encontrada.");
        } else {
            System.out.println("Saldo da conta " + numeroConta + ": R$" + saldo);
        }
    }

    private void exibirEstatisticasBanco() {
        EstatisticasBanco stats = banco.exibirEstatisticasBancoData();
        System.out.println("Estatísticas do Banco:");
        System.out.println("Total de Contas Correntes: " + stats.getTotalContasCorrentes());
        System.out.printf("Saldo Total em Contas Correntes: R$ %.2f\n", stats.getSaldoTotalCorrente());
        System.out.println("Total de Contas Poupança: " + stats.getTotalContasPoupanca());
        System.out.printf("Saldo Total em Contas Poupança: R$ %.2f\n", stats.getSaldoTotalPoupanca());
        System.out.println("Total de Contas: " + stats.getTotalContas());
        System.out.printf("Saldo Total no Banco: R$ %.2f\n", stats.getSaldoTotalBanco());
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

    private void aplicarRendimentosPoupanca() {
        System.out.println("Aplicar rendimentos em contas poupança");
        List<ContaPoupanca> aplicadas = banco.aplicarRendimentosPoupancaData();
        if (aplicadas.isEmpty()) {
            System.out.println("Nenhuma conta poupança encontrada para aplicar rendimentos.");
        } else {
            for (ContaPoupanca cp : aplicadas) {
                System.out.printf("Rendimento aplicado na conta %s. Novo saldo: R$ %.2f\n", cp.getNumero(), cp.getSaldo());
            }
        }
    }
}
