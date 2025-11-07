package service;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaPoupanca;

public class Banco {
    private List<Cliente> clientes;
    private List<Conta> contas;
    private int proximoNumeroConta;

    public Banco() {
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
        this.proximoNumeroConta = 1;
    }

    public Cliente cadastrarCliente(String nome, String cpf, String endereco) {
        if (buscarClientePorCpf(cpf) != null) {
            System.out.println("Erro: Cliente com CPF " + cpf + " já cadastrado.");
            return null;
        }
        Cliente novoCliente = new Cliente(nome, cpf, endereco);
        this.clientes.add(novoCliente);
        System.out.println("Cliente cadastrado com sucesso: " + novoCliente);
        return novoCliente;
    }

    public Conta criarConta(String cpfCliente, String tipoConta, double saldoInicial) {
        Cliente titular = buscarClientePorCpf(cpfCliente);
        if (titular == null) {
            System.out.println("Erro: Cliente com CPF " + cpfCliente + " não encontrado.");
            return null;
        }

        String numeroConta = String.valueOf(proximoNumeroConta++);

        if (tipoConta.equalsIgnoreCase("corrente")) {
            Conta conta = new ContaCorrente(numeroConta, titular, saldoInicial);
            contas.add(conta);
            System.out.println("Conta corrente criada com sucesso: " + conta);
            return conta;
        } else if (tipoConta.equalsIgnoreCase("poupanca")) {
            Conta conta = new ContaPoupanca(numeroConta, titular, saldoInicial, 6.167);
            contas.add(conta);
            System.out.println("Conta poupança criada com sucesso: " + conta);
            return conta;
        } else {
            System.out.println("Erro: Tipo de conta inválido.");
            return null;
        }
        
    }

    private Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public Conta buscarContaPorNumero(String numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumero().equals(numeroConta)) {
                return conta;
            }
        }
        return null;
    }

    public void listarClientes() {
        System.out.println("Lista de Clientes:");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public void listarContas() {
        System.out.println("Lista de Contas:");
        for (Conta conta : contas) {
            System.out.println(conta);
        }
    }

    public void listarContasOrdenadasPorSaldo() {
        System.out.println("Lista de Contas Ordenadas por Saldo:");
        List<Conta> contasOrdenadas = new ArrayList<>(contas);
        Collections.sort(contasOrdenadas, Comparator.comparingDouble(Conta::getSaldo).reversed());
        for (Conta conta : contasOrdenadas) {
            System.out.println(conta);
        }
    }

    public void fazerDeposito(String numeroConta, double valor) {
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta != null) {
            conta.depositar(valor);
            System.out.println("Depósito de R$" + valor + " realizado na conta " + numeroConta);
            System.out.println("Novo saldo: R$" + conta.getSaldo());
        } else {
            System.out.println("Erro: Conta " + numeroConta + " não encontrada.");
        }

    }

    public void fazerSaque(String numeroConta, double valor) {
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta != null) {
            boolean sucesso = conta.sacar(valor);
            if (sucesso) {
                System.out.println("Saque de R$" + valor + " realizado na conta " + numeroConta);
                System.out.println("Novo saldo: R$" + conta.getSaldo());
            } else {
                System.out.println("Erro: Saldo insuficiente para saque na conta " + numeroConta);
            }
        } else {
            System.out.println("Erro: Conta " + numeroConta + " não encontrada.");
        }
    }

    public void consultarSaldo(String numeroConta) {
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta != null) {
            System.out.println("Saldo da conta " + numeroConta + ": R$" + conta.getSaldo());
        } else {
            System.out.println("Erro: Conta " + numeroConta + " não encontrada.");
        }
    }

    public void exibirEstatisticasBanco() {
        int totalContasCorrentes = 0;
        int totalContasPoupanca = 0;
        double saldoTotalCorrente = 0.0;
        double saldoTotalPoupanca = 0.0;

        for (Conta conta : contas) {
            if (conta instanceof ContaCorrente) {
                totalContasCorrentes++;
                saldoTotalCorrente += conta.getSaldo();
            } else if (conta instanceof ContaPoupanca) {
                totalContasPoupanca++;
                saldoTotalPoupanca += conta.getSaldo();
            }
        }

        int totalContas = totalContasCorrentes + totalContasPoupanca;
        double saldoTotalBanco = saldoTotalCorrente + saldoTotalPoupanca;
        System.out.println("Estatísticas do Banco:");
        System.out.println("Total de Contas Correntes: " + totalContasCorrentes);
        System.out.printf("Saldo Total em Contas Correntes: R$ %.2f\n", saldoTotalCorrente);
        System.out.println("Total de Contas Poupança: " + totalContasPoupanca);
        System.out.printf("Saldo Total em Contas Poupança: R$ %.2f\n", saldoTotalPoupanca);
        System.out.println("Total de Contas: " + totalContas); 
        System.out.printf("Saldo Total no Banco: R$ %.2f\n", saldoTotalBanco);
    }

    public void fazerTransferencia(String numeroContaOrigem, String numeroContaDestino, double valor) {
        Conta contaOrigem = buscarContaPorNumero(numeroContaOrigem);
        Conta contaDestino = buscarContaPorNumero(numeroContaDestino);

        if (contaOrigem == null) {
            System.out.println("Erro: Conta de origem " + numeroContaOrigem + " não encontrada.");
            return;
        }
        if (contaDestino == null) {
            System.out.println("Erro: Conta de destino " + numeroContaDestino + " não encontrada.");
            return;
        }

        boolean sucessoSaque = contaOrigem.sacar(valor);
        if (sucessoSaque) {
            contaDestino.depositar(valor);
            System.out.println("Transferência de R$" + valor + " realizada de " + numeroContaOrigem + " para " + numeroContaDestino);
            System.out.printf("Novo saldo da conta %.2f " + numeroContaOrigem + ": R$" + contaOrigem.getSaldo());
            System.out.printf("Novo saldo da conta %.2f" + numeroContaDestino + ": R$" + contaDestino.getSaldo());
        } else {
            System.out.println("Erro: Saldo insuficiente para transferência na conta " + numeroContaOrigem);
        }
    }

    public void aplicarRendimentosPoupanca() {
        int aplicada = 0;
        for (Conta conta : contas) {
            if (conta instanceof ContaPoupanca) {
                ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
                System.out.println("Aplicando rendimento de " + contaPoupanca.getTaxaJuros() + "% na conta " + contaPoupanca.getNumero());
                contaPoupanca.aplicarJuros();
                System.out.printf("Rendimento aplicado na conta %s. Novo saldo: R$ %.2f\n",contaPoupanca.getNumero(), contaPoupanca.getSaldo());
                aplicada++;
    }
        }
        if (aplicada == 0) {
            System.out.println("Nenhuma conta poupança encontrada para aplicar rendimentos.");
        }
    }
}
