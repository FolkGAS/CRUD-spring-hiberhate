package userstable.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import userstable.model.UserEntity;
import userstable.model.UsersFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private SessionFactory sessionFactory;
    private UsersFilter queryFilter;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(UserEntity userEntity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(userEntity);
        logger.info("User added. User : " + userEntity);
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        Session session = sessionFactory.getCurrentSession();
        session.update(userEntity);
        logger.info("User updated. User : " + userEntity);
    }

    @Override
    public void removeUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        UserEntity userEntity = session.load(UserEntity.class, new Integer(id));
        if (userEntity != null) {
            session.delete(userEntity);
            logger.info("User removed. User : " + userEntity);
        } else {
            logger.info("Removing cancelled: user not found. User id : " + id);
        }
    }

    @Override
    public UserEntity getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        UserEntity userEntity = session.load(UserEntity.class, new Integer(id));
        if (userEntity != null) {
            logger.info("Getting user by ID = " + id + ". User : " + userEntity);
        } else {
            logger.info("User not found by ID. User id : " + id);
        }
        return userEntity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserEntity> listUsers(UsersFilter filter) {

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cquery = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = cquery.from(UserEntity.class);

        cquery.select(root).where(getExpression(filter, builder, root));
        int length = queryFilter.getEntriesPerPage();
        int start = (queryFilter.getPage() - 1) * length ;
        List<UserEntity> usersList = session.createQuery(cquery).
                setFirstResult(start).
                setMaxResults(length).getResultList();

        if (usersList.size() > 0)
            for (UserEntity userEntity : usersList)
                logger.info("UsersListEntity: " + userEntity);
        else
            logger.info("UsersList is empty.");
        return usersList;
    }

    @Override
    public int usersCount(UsersFilter filter) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> cquery = builder.createQuery(Long.class);
        Root<UserEntity> root = cquery.from(UserEntity.class);

        cquery.select(builder.count(root)).where(getExpression(filter, builder, root));
        int count = ((Long)session.createQuery(cquery).getSingleResult()).intValue();
        return count;
    }

    public Expression<Boolean> getExpression(UsersFilter filter, CriteriaBuilder builder, Root<UserEntity> root) {
        queryFilter = filter.clone();

        if (queryFilter.getIdStart() == null || queryFilter.getIdStart() < 0)
            queryFilter.setIdStart(0);
        if (queryFilter.getIdEnd() == null)
            queryFilter.setIdEnd(Integer.MAX_VALUE);
        if (queryFilter.getName() == null || filter.getName().trim().equals(""))
            queryFilter.setName("%");
        if (queryFilter.getAgeStart() == null || queryFilter.getAgeStart() < 0)
            queryFilter.setAgeStart(0);
        if (queryFilter.getAgeEnd() == null)
            queryFilter.setAgeEnd(Integer.MAX_VALUE);
        if (queryFilter.getDateStart() == null || !queryFilter.getDateStart().trim().matches("\\d{4}/\\d{2}/\\d{2}"))
            queryFilter.setDateStart("2000/01/01");
        if (queryFilter.getDateEnd() == null || !queryFilter.getDateEnd().trim().matches("\\d{4}/\\d{2}/\\d{2}"))
            queryFilter.setDateEnd("3000/01/01");
        if (queryFilter.getEntriesPerPage() < 1)
            queryFilter.setEntriesPerPage(5);
        if (queryFilter.getPage() < 1)
            queryFilter.setPage(1);

        Timestamp fromDate = new Timestamp(new Date(queryFilter.getDateStart().trim()).getTime());
        Timestamp toDate = new Timestamp(new Date(queryFilter.getDateEnd().trim()).getTime());

        Expression<Boolean> expression = (builder.and(
                builder.between(root.get("id"), queryFilter.getIdStart(), queryFilter.getIdEnd()),
                builder.like(root.get("name"), "%" + queryFilter.getName() + "%"),
                builder.between(root.get("age"), queryFilter.getAgeStart(), queryFilter.getAgeEnd()),
                builder.between(root.get("createdDate"), fromDate, toDate)));
        if (queryFilter.isAdmin())
            expression = builder.and(expression, builder.equal(root.get("isAdmin"), queryFilter.isAdmin()));
        return expression;

    }
}
