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
        List<Conversation> conversations = getLatestConversationsPageQuery(user)
                .orderBy(qConversation.updateDate.desc())
                .offset(params.getPage()).limit(params.getSize())
                .fetch();

        long count = getLatestConversationsPageQuery(user)
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

    private JPAQuery<Conversation> getLatestConversationsPageQuery(User user) {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        QConversation conversation = QConversation.conversation;
        return qf.selectFrom(conversation).where(conversation.members.contains(user));
    }

    public boolean isConversationMember(Conversation conv, User user) {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        QConversation conversation = QConversation.conversation;
        return qf.selectOne().from(conversation)
                .where(
                        conversation.eq(conv),
                        conversation.members.contains(user)
                )
                .fetchCount() > 0;
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
