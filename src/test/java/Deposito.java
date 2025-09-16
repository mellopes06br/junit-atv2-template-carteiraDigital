
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.api.BeforeEach;

import com.example.DigitalWallet;


class Deposito {
        
    DigitalWallet digitalWallet;

    @BeforeEach                                                               //cria um objeto que pode ser utilizado em todos os testes
    public void setUp(){
        digitalWallet = new DigitalWallet("Mel", 0);
    }

        @ParameterizedTest
        @ValueSource(doubles = {10.0, 0.01, 999.99})
        void deveDepositarValoresValidos(double amount) {
            double initialValue = digitalWallet.getBalance();                   //traz o valor inicial 
            digitalWallet.deposit(amount);                                      //valor do depósito
            assertEquals(amount + initialValue, digitalWallet.getBalance());    //realiza e traz o valor pós depósito
        }
        
        @ParameterizedTest
        @ValueSource(doubles={-10.0, -0.01, -999.99})
        void deveLancarExcecaoParaDepositoInvalido(double amount) {
            
            IllegalArgumentException exception = 
            assertThrows(IllegalArgumentException.class, ()-> digitalWallet.deposit(amount)); //exception para saldo inicial negativo
            assertEquals("Amount must be > 0", exception.getMessage());
        }
    }