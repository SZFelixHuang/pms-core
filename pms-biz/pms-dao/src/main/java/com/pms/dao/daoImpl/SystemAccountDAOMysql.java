package com.pms.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pms.commons.exception.DAOException;
import com.pms.dao.SystemAccountDAO;
import com.pms.dao.daoSql.SystemAccountSQLConstant;
import com.pms.entity.Credential;
import com.pms.entity.Principal;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class SystemAccountDAOMysql implements SystemAccountDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	@Override
	public Principal getPrincipal(String agency, String loginName) throws DAOException
	{
		List<Principal> principals = dbAccessor.search(SystemAccountSQLConstant.SELECT_PRINCIPAL,
			new Object[] {agency, loginName}, newPrincipalProcess());
		if (principals.size() > 0)
		{
			return principals.get(0);
		}
		return null;
	}

	@Override
	public Credential getCredential(String agency, String loginName) throws DAOException
	{
		List<Credential> credential = dbAccessor.search(SystemAccountSQLConstant.SELECT_CREDENTIAL,
			new Object[] {agency, loginName}, newCredentialProcess());
		if (credential.size() > 0)
		{
			return credential.get(0);
		}
		return null;
	}

	@Override
	public void createPrincipal(Principal principal) throws DAOException
	{
		dbAccessor.execute(SystemAccountSQLConstant.CREATE_PRINCIPAL,
			new Object[] {principal.getAgency(), principal.getLoginName(), principal.getDisplayName(),
					principal.getIcon(), principal.getAdmin(), principal.getEnable()});
	}

	@Override
	public void createCredential(Credential credential) throws DAOException
	{
		dbAccessor.execute(SystemAccountSQLConstant.CREATE_CREDENTIAL,
			new Object[] {credential.getAgency(), credential.getLoginName(), credential.getPassword(),
					credential.getChangeNextLogin(), credential.getUpdateDate(), credential.getExpireDate()});
	}

	@Override
	public void updatePrincipal(Principal principal) throws DAOException
	{
		dbAccessor.execute(SystemAccountSQLConstant.UPDATE_PRINCIPAL,
			new Object[] {principal.getDisplayName(), principal.getIcon(), principal.getAdmin(), principal.getEnable(),
					principal.getAgency(), principal.getLoginName()});
	}

	@Override
	public void updateCredential(Credential credential) throws DAOException
	{
		dbAccessor.execute(SystemAccountSQLConstant.UPDATE_CREDENTIAL,
			new Object[] {credential.getPassword(), credential.getUpdateDate(), credential.getExpireDate(),
					credential.getChangeNextLogin(), credential.getAgency(), credential.getLoginName()});
	}

	@Override
	public void deleteCredential(String agency, String loginName) throws DAOException
	{
		dbAccessor.execute(SystemAccountSQLConstant.DELETE_CREDENTIAL, new Object[] {agency, loginName});
	}

	@Override
	public void deletePrincipal(String agency, String loginName) throws DAOException
	{
		dbAccessor.execute(SystemAccountSQLConstant.DELETE_PRINCIPAL, new Object[] {agency, loginName});
	}

	private ResultSetProcessor<Principal> newPrincipalProcess()
	{
		return new ResultSetProcessor<Principal>()
		{
			@Override
			public Principal processResultSet(ResultSet rs) throws SQLException
			{
				Principal principal = new Principal();
				principal.setAgency(rs.getString("AGENCY"));
				principal.setLoginName(rs.getString("LOGIN_NAME"));
				principal.setDisplayName(rs.getString("DISPLAY_NAME"));
				principal.setIcon(rs.getString("ICON"));
				principal.setAdmin(rs.getBoolean("IS_ADMIN"));
				principal.setEnable(rs.getBoolean("IS_ENABLE"));
				return principal;
			}
		};
	}

	private ResultSetProcessor<Credential> newCredentialProcess()
	{
		return new ResultSetProcessor<Credential>()
		{
			@Override
			public Credential processResultSet(ResultSet rs) throws SQLException
			{
				Credential credential = new Credential();
				credential.setAgency(rs.getString("AGENCY"));
				credential.setLoginName(rs.getString("LOGIN_NAME"));
				credential.setPassword(rs.getString("PASSWORD"));
				credential.setChangeNextLogin(rs.getBoolean("CHANGE_NEXT_LOGIN"));
				credential.setExpireDate(rs.getDate("EXPIRE_DATE"));
				credential.setUpdateDate(rs.getDate("UPDATE_DATE"));
				return credential;
			}
		};
	}

	@Override
	public List<Principal> getAllPrincipals(String agency) throws DAOException
	{
		return dbAccessor.search(SystemAccountSQLConstant.SELECT_ALL_PRINCIPALS, new Object[] {agency},
			newPrincipalProcess());
	}

	@Override
	public void createRelationshipWithGroup(String agency, String loginName, Integer groupId) throws DAOException
	{
		dbAccessor.execute(SystemAccountSQLConstant.CREATE_RELATIONSHIP_WITH_GROUP,
			new Object[] {agency, loginName, groupId});
	}

	@Override
	public List<Integer> getRelationshipWithGroup(String agency, String loginName) throws DAOException
	{
		return dbAccessor.search(SystemAccountSQLConstant.GET_RELATIONSHIP_WITH_GROUP, new Object[] {agency, loginName},
			new ResultSetProcessor<Integer>()
			{
				@Override
				public Integer processResultSet(ResultSet rs) throws SQLException
				{
					return rs.getInt("GROUP_ID");
				}
			});
	}

	@Override
	public void deleteRelationshipWithGroup(String agency, String loginName) throws DAOException
	{
		dbAccessor.execute(SystemAccountSQLConstant.DELETE_RELATIONSHIP_WITH_GROUP, new Object[] {agency, loginName});
	}

	@Override
	public List<Principal> getActivePrincipals(String agency) throws DAOException
	{
		return this.dbAccessor.search(SystemAccountSQLConstant.SELECT_ALL_ACTIVE_PRINCIPALS, new Object[] {agency},
			newPrincipalProcess());
	}
}