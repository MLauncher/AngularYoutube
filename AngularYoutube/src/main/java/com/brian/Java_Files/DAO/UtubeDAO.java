package main.java.com.brian.Java_Files.DAO;

import javax.transaction.TransactionScoped;

import main.java.com.brian.Java_Files.hibernateFiles3.Videos;


import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;






import java.util.List;


@Component
@Transactional
public class UtubeDAO {
	
	
	

	
	@Autowired
	SessionFactory sessionFactory;
	
	
	List<Videos> vids;
	public Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	

	public List<Videos> getVideos()
	{
		Query query = getCurrentSession().createQuery("from Videos");
		
		System.out.println(query.list());
		vids = query.list();
		return vids;
	}

}
