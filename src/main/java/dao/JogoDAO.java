package dao;

import java.util.List;

import javax.persistence.EntityManager;

import entidades.Jogo;
import util.JPAUtil;

public class JogoDAO {
    private EntityManager em;

    public JogoDAO() {
        this.em = JPAUtil.criarEntityManager();
    }

    public void salvar(Jogo jogo) {
        em.getTransaction().begin();
        em.persist(jogo);
        em.getTransaction().commit();
    }

    public void editar(Jogo jogo) {
        em.getTransaction().begin();
        em.merge(jogo);
        em.getTransaction().commit();
    }

    public void excluir(Jogo jogo) {
        em.getTransaction().begin();
        em.remove(em.contains(jogo) ? jogo : em.merge(jogo));
        em.getTransaction().commit();
    }

    public List<Jogo> listar() {
        return em.createQuery("from Jogo", Jogo.class).getResultList();
    }
}
