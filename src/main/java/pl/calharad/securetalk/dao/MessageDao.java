package pl.calharad.securetalk.dao;

import pl.calharad.securetalk.entity.Message;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

@RequestScoped
@Transactional
public class MessageDao extends BaseDao<Message, Long> {

    @Override
    public void save(Message obj) {
        if(obj.getId() == null) {
            em.persist(obj);
        } else {
            em.merge(obj);
        }
    }

    @Override
    protected Class<Message> getType() {
        return Message.class;
    }
}
