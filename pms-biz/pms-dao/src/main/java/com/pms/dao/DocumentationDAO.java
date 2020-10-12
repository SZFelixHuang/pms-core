package com.pms.dao;

import java.io.InputStream;

import com.pms.commons.exception.DAOException;
import com.pms.entity.DocumentationModel;

public interface DocumentationDAO extends PMSDAO<DocumentationModel, Integer>
{
	public void insertDocumentationContent(String agency, String fileKey, InputStream in)
			throws DAOException;

	public void deleteDocumentation(String agency, String fileKey) throws DAOException;

	public InputStream getDocumentContent(String agency, String fileKey) throws DAOException;

	public void updateDocumentation(String agency, String fileKey, InputStream in) throws DAOException;

	public DocumentationModel getDocumentModel(String agency, String fileKey) throws DAOException;
}