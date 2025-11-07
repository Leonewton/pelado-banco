package model;
public class ContaPoupanca extends Conta {

    private double taxaJuros;

    public ContaPoupanca(String numero, Cliente titular, double saldoInicial, double taxaJuros) {
        super(numero, titular, saldoInicial);
        this.taxaJuros = taxaJuros;
    }

    public void aplicarJuros() {
        double juros = saldo * taxaJuros / 100;
        depositar(juros);
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    @Override
    public String toString() {
        return "ContaPoupança{" +
                "numero='" + getNumero() + '\'' +
                ", saldo=" + getSaldo() +
                ", titular=" + getTitular() +
                ", taxaJuros=" + taxaJuros +
                '}';
    }
}