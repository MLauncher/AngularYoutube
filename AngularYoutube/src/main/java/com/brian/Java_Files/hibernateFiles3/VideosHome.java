package main.java.com.brian.Java_Files.hibernateFiles3;

// Generated May 22, 2016 12:26:28 PM by Hibernate Tools 4.3.1


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Videos.
 * @see hibernateFiles3.Videos
 * @author Hibernate Tools
 */

public class VideosHome {

	private static final Log log = LogFactory.getLog(VideosHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Videos transientInstance) {
		log.debug("persisting Videos instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Videos persistentInstance) {
		log.debug("removing Videos instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Videos merge(Videos detachedInstance) {
		log.debug("merging Videos instance");
		try {
			Videos result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Videos findById(Integer id) {
		log.debug("getting Videos instance with id: " + id);
		try {
			Videos instance = entityManager.find(Videos.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
