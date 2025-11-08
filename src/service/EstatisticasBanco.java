package service;

public class EstatisticasBanco {
    private final int totalContasCorrentes;
    private final double saldoTotalCorrente;
    private final int totalContasPoupanca;
    private final double saldoTotalPoupanca;
    private final int totalContas;
    private final double saldoTotalBanco;

    public EstatisticasBanco(int totalContasCorrentes, double saldoTotalCorrente, int totalContasPoupanca, double saldoTotalPoupanca, int totalContas, double saldoTotalBanco) {
        this.totalContasCorrentes = totalContasCorrentes;
        this.saldoTotalCorrente = saldoTotalCorrente;
        this.totalContasPoupanca = totalContasPoupanca;
        this.saldoTotalPoupanca = saldoTotalPoupanca;
        this.totalContas = totalContas;
        this.saldoTotalBanco = saldoTotalBanco;
    }

    public int getTotalContasCorrentes() {
        return totalContasCorrentes;
    }

    public double getSaldoTotalCorrente() {
        return saldoTotalCorrente;
    }

    public int getTotalContasPoupanca() {
        return totalContasPoupanca;
    }

    public double getSaldoTotalPoupanca() {
        return saldoTotalPoupanca;
    }

    public int getTotalContas() {
        return totalContas;
    }

    public double getSaldoTotalBanco() {
        return saldoTotalBanco;
    }
}
