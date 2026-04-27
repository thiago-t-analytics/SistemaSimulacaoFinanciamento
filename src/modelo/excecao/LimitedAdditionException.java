package modelo.excecao;

// Exceção para representar a violacao da regra de negócio de acréscimo
public class LimitedAdditionException extends Exception {
    public LimitedAdditionException(String mensagem) {
        super(mensagem);
    }
}
