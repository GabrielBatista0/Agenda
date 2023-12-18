package bosch.AgendaETS.model.usuario;

public record DadosUsuario (
        int id,
        String usuario,
        String senha
){
    public DadosUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getUsuario(), usuario.getSenha());
    }
}
