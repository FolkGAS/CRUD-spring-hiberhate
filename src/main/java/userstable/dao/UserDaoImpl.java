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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    private SessionFactory sessionFactory;

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
        UserEntity userEntity = session.load(UserEntity.class, id);
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
        UserEntity userEntity = session.load(UserEntity.class, id);
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
        CriteriaQuery<UserEntity> cQuery = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = cQuery.from(UserEntity.class);

        cQuery.select(root).where(getExpression(filter, builder, root));
        int length = filter.getUsersPerPage();
        int start = (filter.getPage() - 1) * length;
        List<UserEntity> usersList = session.createQuery(cQuery).
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
        return (session.createQuery(cquery).getSingleResult()).intValue();
    }

    // Getting Expression by filter for Hibernate.javax.persistence query.
    private Expression<Boolean> getExpression(UsersFilter filter, CriteriaBuilder builder, Root<UserEntity> root) {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Expression<Boolean> expression = builder.ge(root.get("id"), 0);

        //Set default users per page value.
        if (filter.getUsersPerPage() < 1)
            filter.setUsersPerPage(10);

        //Set default page value.
        if (filter.getPage() < 1)
            filter.setPage(1);

        if (filter.getIdStart() != null && filter.getIdStart() >= 0)
            expression = builder.and(expression, builder.ge(root.get("id"), filter.getIdStart()));

        if (filter.getIdEnd() != null)
            expression = builder.and(expression, builder.le(root.get("id"), filter.getIdEnd()));

        if (filter.getName() != null && !filter.getName().trim().equals(""))
            expression = builder.and(expression, builder.like(root.get("name"), "%" + filter.getName() + "%"));

        if (filter.getAgeStart() != null && filter.getAgeStart() >= 0)
            expression = builder.and(expression, builder.ge(root.get("age"), filter.getAgeStart()));

        if (filter.getAgeEnd() != null)
            expression = builder.and(expression, builder.le(root.get("age"), filter.getAgeEnd()));

        if (filter.isAdmin())
            expression = builder.and(expression, builder.equal(root.get("isAdmin"), filter.isAdmin()));

        // Check date for yyyy/MM/dd.
        if (filter.getDateStart() != null && filter.getDateStart().trim().matches("\\d{4}/\\d{2}/\\d{2}")) {
            Timestamp fromDate;
            try {
                fromDate = new Timestamp(formatter.parse(filter.getDateStart().trim()).getTime());
            } catch (ParseException e) {
                logger.info("Wrong date format. Date: " + filter.getDateStart());
                fromDate = new Timestamp(0);
            }
            expression = builder.and(expression, builder.greaterThanOrEqualTo(root.get("createdDate"), fromDate));
        }

        // Check date for yyyy/MM/dd.
        if (filter.getDateEnd() != null && filter.getDateEnd().trim().matches("\\d{4}/\\d{2}/\\d{2}")) {
            Timestamp toDate;
            try {
                toDate = new Timestamp(formatter.parse(filter.getDateEnd().trim()).getTime());
            } catch (ParseException e) {
                logger.info("Wrong date format. Date: " + filter.getDateEnd());
                toDate = new Timestamp(Integer.MAX_VALUE);
            }
            expression = builder.and(expression, builder.lessThanOrEqualTo(root.get("createdDate"), toDate));
        }
        return expression;
    }
}
