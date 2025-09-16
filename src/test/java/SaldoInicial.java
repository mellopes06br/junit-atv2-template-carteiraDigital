import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;

import com.example.DigitalWallet;

class SaldoInicial {

       @Test
        void deveConfigurarSaldoInicialCorreto() {
           DigitalWallet digitalWallet = new DigitalWallet("Mel", 0);  //
           assertEquals(0, digitalWallet.getBalance());
        }

        @ParameterizedTest
        @ValueSource(doubles={-24,-2,-48})
        void deveLancarExcecaoParaSaldoInicialNegativo(double balance) {
         
         IllegalArgumentException exception = 
         assertThrows(IllegalArgumentException.class, ()-> new DigitalWallet("Mel", balance));
             assertEquals("Negative initial balance", exception.getMessage());
        }
    }