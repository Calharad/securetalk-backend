package pl.calharad.securetalk.dao;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public abstract class BaseDao <T, ID> {

    @Inject
    protected EntityManager em;

    public T getOne(ID id) throws EntityNotFoundException {
        Optional<T> entity = findById(id);
        if(entity.isEmpty()) {
            throw new EntityNotFoundException(String.format("Entity %s not found", getType().getSimpleName()));
        }
        return entity.get();
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(getType(), id));
    }
    public void saveAll(Iterable<T> obj) {
        obj.forEach(this::save);
    }

    public void delete(T obj) {
        em.remove(obj);
    }

    public long count() {
        JPAQueryFactory qf = new JPAQueryFactory(em);
        var entity = getEntity();
        return qf.selectFrom(entity).fetchCount();
    }

    public void flushSaveAll(Iterable<T> obj) {
        saveAll(obj);
        em.flush();
    }

    public void flushSave(T obj) {
        save(obj);
        em.flush();
    }

    public abstract void save(T obj);
    protected abstract Class<T> getType();
    protected abstract EntityPathBase<T> getEntity();
}
