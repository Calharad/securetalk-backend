package pl.calharad.securetalk.dao;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import pl.calharad.securetalk.entity.Conversation;
import pl.calharad.securetalk.entity.QConversation;
import pl.calharad.securetalk.entity.User;
import pl.calharad.securetalk.utils.page.Page;
import pl.calharad.securetalk.utils.page.PageParams;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional
public class ConversationDao extends BaseDao<Conversation, Long> {

    public Page<Conversation> getLatestConversationsPageForUser(User user, PageParams params) {

        QConversation qConversation = QConversation.conversation;
        List<Conversation> conversations = getLatestConversationsPageQuery()
                .orderBy(qConversation.updateDate.desc())
                .offset(params.getPage()).limit(params.getSize())
                .fetch();

        long count = getLatestConversationsPageQuery()
                .fetchCount();
        long pages = (count - 1) / params.getSize() + 1;

        return Page.<Conversation>builder()
                .content(conversations)
                .page(params.getPage())
                .size(params.getSize())
                .totalElements(count)
                .totalPages(Long.valueOf(pages).intValue())
                .build();
    }

    private JPAQuery<Conversation> getLatestConversationsPageQuery() {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        return qf.selectFrom(QConversation.conversation);
    }

    @Override
    public void save(Conversation obj) {
        if (obj.getId() == null) {
            em.persist(obj);
        } else {
            em.merge(obj);
        }
    }

    @Override
    protected Class<Conversation> getType() {
        return Conversation.class;
    }

    @Override
    protected EntityPathBase<Conversation> getEntity() {
        return QConversation.conversation;
    }
}
