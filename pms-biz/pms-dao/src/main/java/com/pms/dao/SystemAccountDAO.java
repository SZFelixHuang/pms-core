package com.pms.dao;

import java.util.List;

import com.pms.commons.exception.DAOException;
import com.pms.entity.Credential;
import com.pms.entity.Principal;

public interface SystemAccountDAO
{
	public List<Principal> getAllPrincipals(String agency) throws DAOException;

	public Principal getPrincipal(String agency, String loginName) throws DAOException;

	public Credential getCredential(String agency, String loginName) throws DAOException;

	public void createPrincipal(Principal principal) throws DAOException;

	public void createCredential(Credential credential) throws DAOException;

	public void updatePrincipal(Principal principal) throws DAOException;

	public void updateCredential(Credential credential) throws DAOException;

	public void deletePrincipal(String agency, String loginName) throws DAOException;

	public void deleteCredential(String agency, String loginName) throws DAOException;

	public void createRelationshipWithGroup(String agency, String loginName, Integer groupId) throws DAOException;

	public List<Integer> getRelationshipWithGroup(String agency, String loginName) throws DAOException;

	public void deleteRelationshipWithGroup(String agency, String loginName) throws DAOException;

	public List<Principal> getActivePrincipals(String agency) throws DAOException;
}