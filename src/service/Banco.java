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
            return null;
        }
        Cliente novoCliente = new Cliente(nome, cpf, endereco);
        this.clientes.add(novoCliente);
        return novoCliente;
    }

    public Conta criarConta(String cpfCliente, String tipoConta, double saldoInicial) {
        Cliente titular = buscarClientePorCpf(cpfCliente);
        if (titular == null) {
            return null;
        }

        String numeroConta = String.valueOf(proximoNumeroConta++);

        if (tipoConta.equalsIgnoreCase("corrente")) {
            Conta conta = new ContaCorrente(numeroConta, titular, saldoInicial);
            contas.add(conta);
            return conta;
        } else if (tipoConta.equalsIgnoreCase("poupanca")) {
            Conta conta = new ContaPoupanca(numeroConta, titular, saldoInicial, 6.167);
            contas.add(conta);
            return conta;
        } else {
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

    public boolean clienteExiste(String cpf) {
        return buscarClientePorCpf(cpf) != null;
    }

    public Conta buscarContaPorNumero(String numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumero().equals(numeroConta)) {
                return conta;
            }
        }
        return null;
    }

    public List<Cliente> listarClientesData() {
        return new ArrayList<>(clientes);
    }

    public List<Conta> listarContasData() {
        return new ArrayList<>(contas);
    }

    public List<Conta> listarContasOrdenadasPorSaldoData() {
        List<Conta> contasOrdenadas = new ArrayList<>(contas);
        Collections.sort(contasOrdenadas, Comparator.comparingDouble(Conta::getSaldo).reversed());
        return contasOrdenadas;
    }

    public boolean fazerDepositoData(String numeroConta, double valor) {
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta != null) {
            conta.depositar(valor);
            return true;
        }
        return false;
    }

    public boolean fazerSaqueData(String numeroConta, double valor) {
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta != null) {
            return conta.sacar(valor);
        }
        return false;
    }

    public Double consultarSaldoData(String numeroConta) {
        Conta conta = buscarContaPorNumero(numeroConta);
        if (conta != null) {
            return conta.getSaldo();
        }
        return null;
    }

    public EstatisticasBanco exibirEstatisticasBancoData() {
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
        return new EstatisticasBanco(totalContasCorrentes, saldoTotalCorrente, totalContasPoupanca, saldoTotalPoupanca, totalContas, saldoTotalBanco);
    }

    public boolean fazerTransferenciaData(String numeroContaOrigem, String numeroContaDestino, double valor) {
        Conta contaOrigem = buscarContaPorNumero(numeroContaOrigem);
        Conta contaDestino = buscarContaPorNumero(numeroContaDestino);

        if (contaOrigem == null || contaDestino == null) {
            return false;
        }

        boolean sucessoSaque = contaOrigem.sacar(valor);
        if (sucessoSaque) {
            contaDestino.depositar(valor);
            return true;
        }
        return false;
    }

    public List<ContaPoupanca> aplicarRendimentosPoupancaData() {
        List<ContaPoupanca> aplicadas = new ArrayList<>();
        for (Conta conta : contas) {
            if (conta instanceof ContaPoupanca) {
                ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
                contaPoupanca.aplicarJuros();
                aplicadas.add(contaPoupanca);
            }
        }
        return aplicadas;
    }
}
