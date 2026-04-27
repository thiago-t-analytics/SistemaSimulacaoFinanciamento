package modelo.excecao;

// Exceção personalizada para representar uma violação da regra de negócio de acréscimo.
public class LimitedAdditionException extends Exception {
    public LimitedAdditionException(String mensagem) {
        super(mensagem);
    }
}
