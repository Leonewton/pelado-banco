public class ContaCorrente extends Conta {

    public ContaCorrente(String numero, Cliente titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    @Override
    public String toString() {
        return "ContaCorrente{" +
                "numero='" + getNumero() + '\'' +
                ", saldo=" + getSaldo() +
                ", titular=" + getTitular() +
                '}';
    }
}