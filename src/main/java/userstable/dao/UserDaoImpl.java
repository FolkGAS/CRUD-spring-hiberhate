package userstable.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import userstable.model.UserEntity;
import userstable.model.UsersFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    public List<UserEntity> listUsers(UsersFilter filter, int page, int length) {
        int start = (page - 1) * length;
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cquery = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = cquery.from(UserEntity.class);

        setQueryFilter(filter);
        Timestamp fromDate = new Timestamp(new Date(queryFilter.getDateStart()).getTime());
        Timestamp toDate = new Timestamp(new Date(queryFilter.getDateEnd()).getTime());

        cquery.select(root).where((builder.and(
                builder.between(root.get("id"), queryFilter.getIdStart(), queryFilter.getIdEnd()),
                builder.like(root.get("name"), "%" + queryFilter.getName() + "%"),
                builder.between(root.get("age"), queryFilter.getAgeStart(), queryFilter.getAgeEnd()),
                builder.equal(root.get("isAdmin"), queryFilter.isAdmin()),
                builder.between(root.get("createdDate"), fromDate, toDate))));

        List<UserEntity> usersList = session.createQuery(cquery).setFirstResult(start).setMaxResults(length).getResultList();

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

        setQueryFilter(filter);
        Timestamp fromDate = new Timestamp(new Date(queryFilter.getDateStart()).getTime());
        Timestamp toDate = new Timestamp(new Date(queryFilter.getDateEnd()).getTime());

        cquery.select(builder.count(root)).where((builder.and(
                builder.between(root.get("id"), queryFilter.getIdStart(), queryFilter.getIdEnd()),
                builder.like(root.get("name"), "%" + queryFilter.getName() + "%"),
                builder.between(root.get("age"), queryFilter.getAgeStart(), queryFilter.getAgeEnd()),
                builder.equal(root.get("isAdmin"), queryFilter.isAdmin()),
                builder.between(root.get("createdDate"), fromDate, toDate))));
        int count = ((Long)session.createQuery(cquery).getSingleResult()).intValue();
        return count;
    }

    public void setQueryFilter(UsersFilter filter) {
        queryFilter = filter.clone();

        if (queryFilter.getIdStart() == null)
            queryFilter.setIdStart(0);
        if (queryFilter.getIdEnd() == null)
            queryFilter.setIdEnd(Integer.MAX_VALUE);
        if (queryFilter.getName() == null || filter.getName().trim().equals(""))
            queryFilter.setName("%");
        if (queryFilter.getAgeStart() == null)
            queryFilter.setAgeStart(0);
        if (queryFilter.getAgeEnd() == null)
            queryFilter.setAgeEnd(Integer.MAX_VALUE);
        if (queryFilter.getDateStart() == null || queryFilter.getDateStart().trim().equals(""))
            queryFilter.setDateStart("2000/01/01");
        if (queryFilter.getDateEnd() == null || queryFilter.getDateEnd().trim().equals(""))
            queryFilter.setDateEnd("3000/01/01");
    }
}
