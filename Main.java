public class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("João Silva", "123.456.789-00", "Rua das Flores, 123");
        System.out.println(cliente.toString()); 
        
        Conta conta = new ContaCorrente("0001", cliente, 1000.0);
        System.out.println(conta.toString());
            
        Conta contaPoupanca = new ContaPoupanca("0002", cliente, 500.0, 2.0);
        System.out.println(contaPoupanca.toString());
    };


    
}
