import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;


public class Pagamento {
    
    @ParameterizedTest
    @CsvSource({"100.0, 30.00, true", "50.0, 80.0, false", "10.0, 10.0, true"})
    void pagamentoComCarteiraVerificadaENaoBloqueada(double inicial, double valor, boolean esperado) {

        DigitalWallet digitalWallet = new DigitalWallet("Mel", inicial);                         //cria carteir acom saldo inicial
        
        digitalWallet.verify();       //verifica a carteira
        digitalWallet.unlock();       //desbloqueia a carteira

        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked()); //carteira verificada e não bloqueada.
        assertEquals(esperado, digitalWallet.pay(valor));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10, 0, -0.1})
    void deveLancarExcecaoParaPagamentoInvalido(double valor) {
        DigitalWallet digitalWallet = new DigitalWallet("Mel", 100);

        digitalWallet.unlock();        //verifica a carteira
        digitalWallet.verify();       //desbloqueia a carteira
        
        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked()); //carteira verificada e não bloqueada.
        assertThrows(IllegalArgumentException.class, ()->{
            digitalWallet.pay(valor);
        });

    }
   
    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        DigitalWallet digitalWallet = new DigitalWallet("Mel", 100);
        assertThrows(IllegalStateException.class, ()->{
            digitalWallet.pay(10);
        });
    }
}
