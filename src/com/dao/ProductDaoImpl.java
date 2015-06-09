package com.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.domain.Product;
import com.domain.ShoppingCart;

public class ProductDaoImpl implements ProductDao{
	
	@Resource
	private SessionFactory sessionFactory;

	public List<Product> searchProductBySearchBox(String searchBox) {
		
		String hql="from Product as pro where pro.proName like '%"+searchBox+"%'";
		List<Product> list=sessionFactory.getCurrentSession().createQuery(hql).list();
		return list;
	}

	public List<Product> searchProductByName(String name) {
		String hql="from Product as pro where pro.proName = '"+name+"'";
		List<Product> list=sessionFactory.getCurrentSession().createQuery(hql).list();
		return list;
	}

	public boolean addShoppingCart(String img, String name, double price, int quantity) {
		ShoppingCart shoppingCart=new ShoppingCart();
		//先随便插入个用户名,用户名id
		shoppingCart.setAdminName("username");
		shoppingCart.setImg(img);
		shoppingCart.setName(name);
		shoppingCart.setPrice(price);
		shoppingCart.setQuantity(quantity);
		sessionFactory.getCurrentSession().persist(shoppingCart);
		String hql="from ShoppingCart as ac where ac.name='"+name+"' and ac.quantity='"+quantity+"'";
		List<Product> list=sessionFactory.getCurrentSession().createQuery(hql).list();
		if(list.size()==0)
			return false;
		else
			return true;
	}
}
