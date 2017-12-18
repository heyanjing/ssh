package com.he.ssh.base.repo;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.io.Serializable;

public class BaseRepoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepo<T, ID> {
    private final EntityManager em;
    private final Class<T> entityClass;
    private final String entityName;

    protected DataSource dataSource;


    public BaseRepoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.em = entityManager;
        this.entityClass = entityInformation.getJavaType();
        this.entityName = entityInformation.getEntityName();
        this.dataSource = ((EntityManagerFactoryInfo) entityManager.getEntityManagerFactory()).getDataSource();
    }

    public BaseRepoImpl(Class<T> domainClass, EntityManager em) {
//		super(domainClass, em);
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);

    }



	/*public BaseRepoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
		this.em = entityManager;
		this.entityClass = entityInformation.getJavaType();
		this.entityName = entityInformation.getEntityName();

		this.dataSource = ((EntityManagerFactoryInfo) entityManager.getEntityManagerFactory()).getDataSource();
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	public BaseRepoImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}*/
}
