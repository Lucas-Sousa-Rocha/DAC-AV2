package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import dao.JogoDAO;
import entidades.Jogo;
import java.util.Date;
import java.util.List;
import java.util.Random;

@ManagedBean
@ViewScoped
public class JogoBean {

    private Jogo jogo;
    private List<Jogo> listaJogos;
    private JogoDAO jogoDAO;
    
    /*@PostConstruct
    public void init() {
        EntityManager em = JPAUtil.criarEntityManager();
        jogoDAO = new JogoDAO(em);
        jogo = new Jogo();
        listar();
    }*/

    public void salvar() {
        try {
            // Preencher data de cadastro e número sorteado
            jogo.setDataCadastro(new Date());
            jogo.setNumeroSorteado(new Random().nextInt(10) + 1);

            jogoDAO.salvar(jogo);
            listar(); // Atualizar a lista após salvar
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Jogo salvo com sucesso!"));
            limpar();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar jogo!", e.getMessage()));
        }
    }

    public void excluir(Jogo jogoSelecionado) {
        try {
            jogoDAO.excluir(jogoSelecionado);
            listar(); // Atualizar a lista após exclusão
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Jogo excluído com sucesso!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir jogo!", e.getMessage()));
        }
    }

    public void editar(Jogo jogoSelecionado) {
        this.jogo = jogoSelecionado;
    }

    public void listar() {
        listaJogos = jogoDAO.listar();
    }

    public void limpar() {
        jogo = new Jogo();
    }

    // Método para encontrar o maior número entre v1 e v5
    public int maiorNumero(Jogo jogoSelecionado) {
        int[] valores = {jogoSelecionado.getV1(), jogoSelecionado.getV2(), jogoSelecionado.getV3(),
                         jogoSelecionado.getV4(), jogoSelecionado.getV5()};
        int maior = valores[0];
        for (int valor : valores) {
            if (valor > maior) {
                maior = valor;
            }
        }
        return maior;
    }

    // Método para verificar se o número sorteado está entre os valores v1 a v5
    public boolean isNumeroSorteadoPresente(Jogo jogoSelecionado) {
        return jogoSelecionado.getV1().equals(jogoSelecionado.getNumeroSorteado()) ||
               jogoSelecionado.getV2().equals(jogoSelecionado.getNumeroSorteado()) ||
               jogoSelecionado.getV3().equals(jogoSelecionado.getNumeroSorteado()) ||
               jogoSelecionado.getV4().equals(jogoSelecionado.getNumeroSorteado()) ||
               jogoSelecionado.getV5().equals(jogoSelecionado.getNumeroSorteado());
    }

    // Getters e Setters
    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public List<Jogo> getListaJogos() {
        return listaJogos;
    }

    public void setListaJogos(List<Jogo> listaJogos) {
        this.listaJogos = listaJogos;
    }
}