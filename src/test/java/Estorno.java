import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;



class Estorno {
    static Stream<Arguments> valoresEstorno() {
        return Stream.of(
            Arguments.of(100.0, 10.0, 110.0),
            Arguments.of(0.0,   5.0,   5.0),
            Arguments.of(50.0,  0.01, 50.01)
        );
    }

    @ParameterizedTest
    @MethodSource("valoresEstorno")
    void refundComCarteiraValida(double inicial, double valor, double saldoEsperado) {

        DigitalWallet digitalWallet = new DigitalWallet("Mel", inicial); //cria, desbloqueia e verifica a carteira
        digitalWallet.unlock();
        digitalWallet.verify();

        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked()); //carteira verificada e não bloqueada.
        digitalWallet.refund(valor);
        assertEquals(saldoEsperado, digitalWallet.getBalance()); 

    }

    @ParameterizedTest
    @ValueSource(doubles={-10.0, 0, -0.1})    
    void deveLancarExcecaoParaRefundInvalido(double valor) {

        DigitalWallet digitalWallet = new DigitalWallet("Mel", 50); //cria, desbloqueia e verifica a carteira
        digitalWallet.unlock();
        digitalWallet.unlock();
        digitalWallet.verify();

        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked()); //carteira verificada e não bloqueada.

        assertThrows(IllegalArgumentException.class, () -> digitalWallet.refund(valor),"Amount must be > 0"); //exception para valor menor ou igual a 0
        assertEquals(50, digitalWallet.getBalance(), "Amount must be > 0");
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        
    //carteira não verificada
    DigitalWallet digitalWallet = new DigitalWallet("Mel", 50.0); //cria carteira liberada 
    digitalWallet.unlock();

    assumeFalse(digitalWallet.isVerified()); //condiçao de não verificada

    assertThrows(IllegalStateException.class, () -> digitalWallet.refund(10.0), "Wallet isn't verified."); //exception para carteira não verificada
    assertEquals(50.0, digitalWallet.getBalance(), "Wallet isn't verified.");

  //carteira bloqueada
    DigitalWallet blockedWallet = new DigitalWallet("Mel", 50.0); //cria carteira bloqueada
    blockedWallet.verify();
    blockedWallet.lock();

    assumeFalse(blockedWallet.isLocked()); //condiçao de bloqueada

    assertThrows(IllegalStateException.class, () -> digitalWallet.refund(10.0), "Wallet is locked."); //exception para carteira bloqueada
    assertEquals(50.0, digitalWallet.getBalance(), "Wallet is locked.");

    }
}
