package springmvcdemo.authentication.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import springmvcdemo.authentication.model.User;

@Transactional
@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

	@PersistenceContext(unitName="authEntityManagerFactory")
	protected EntityManager entityManager;

	public List<User> findAllUsers() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

	public User findByUsername(String userName) {
		Query query = entityManager.createQuery("from User where username=?");
		query.setParameter(1, userName);
		List list = query.getResultList();
		if (list.isEmpty() == false)
			return (User) list.get(0);
		return null;
	}

	public User insertUser(User user) {
		try {
			entityManager.persist(user);
			return user;
		} catch (Exception ex) {
			return null;
		}
	}

	public User deleteUser(String userName) {
		
		return null;
	}

}
