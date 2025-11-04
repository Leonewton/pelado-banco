import java.util.ArrayList;
import java.util.List;

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
            Conta conta = new ContaPoupanca(numeroConta, titular, saldoInicial, 0.5);
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
}