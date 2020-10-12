package org.springframework.orm.jpa.vendor;

public class PMSHibernateJpaVendorAdapter extends HibernateJpaVendorAdapter
{
	public void setDataBaseType(String dataBaseType)
	{
		Database database = Database.valueOf(dataBaseType.toUpperCase());
		this.setDatabase(database);
	}
}