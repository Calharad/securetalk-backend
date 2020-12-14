package pl.calharad.securetalk.dao;

import pl.calharad.securetalk.entity.Conversation;
import pl.calharad.securetalk.entity.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class ConversationDao extends BaseDao<Conversation, Long> {
    @Inject
    EntityManager em;


    @Override
    public void save(Conversation obj) {
        if (obj.getId() == null) {
            em.persist(obj);
        } else {
            em.merge(obj);
        }
    }

    @Override
    protected EntityManager getEm() {
        return em;
    }

    @Override
    protected Class<Conversation> getType() {
        return Conversation.class;
    }
}
