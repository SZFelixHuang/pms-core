package com.pms.dao.daoImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.pms.commons.exception.DAOException;
import com.pms.dao.DocumentationDAO;
import com.pms.dao.daoSql.DocumentationSQLConstant;
import com.pms.entity.DocumentationModel;
import com.pms.framework.persistence.DBAccessor;
import com.pms.framework.persistence.ResultSetProcessor;

public class DocumentationDAOMysql extends PMSDAOImpl<DocumentationModel, Integer> implements DocumentationDAO
{
	@Autowired
	private DBAccessor dbAccessor;

	private ResultSetProcessor<OutputStream> documentationOutputProcessor()
	{
		return new ResultSetProcessor<OutputStream>()
		{
			@Override
			public OutputStream processResultSet(ResultSet rs) throws SQLException
			{
				Blob contentField = rs.getBlob(1);
				if (contentField == null)
				{
					return null;
				}
				return contentField.setBinaryStream(1);
			}
		};
	}

	public ResultSetProcessor<InputStream> documentationInputProcessor()
	{
		return new ResultSetProcessor<InputStream>()
		{
			@Override
			public InputStream processResultSet(ResultSet rs) throws SQLException
			{
				Blob contentField = rs.getBlob(1);
				if (contentField == null)
				{
					return null;
				}
				return contentField.getBinaryStream();
			}
		};
	}

	public ResultSetProcessor<DocumentationModel> getDocmentDationModelProcessor()
	{
		return new ResultSetProcessor<DocumentationModel>()
		{
			@Override
			public DocumentationModel processResultSet(ResultSet rs) throws SQLException
			{
				DocumentationModel model = new DocumentationModel();
				model.setId(rs.getInt("ID"));
				model.setAgency(rs.getString("AGENCY"));
				model.setCreateDate(rs.getDate("CREATE_TIME"));
				model.setFileKey(rs.getString("FILE_KEY"));
				model.setFileName(rs.getString("FILE_NAME"));
				model.setFileSize(rs.getLong("FILE_SIZE"));
				model.setFileType(rs.getString("FILE_TYPE"));
				model.setUpdatedDate(rs.getDate("UPDATED_TIME"));
				return model;
			}
		};
	}

	@Override
	public void insertDocumentationContent(final String agency, final String fileKey, final InputStream in) throws DAOException
	{
		this.dbAccessor.update(new PreparedStatementCreator()
		{
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException
			{
				PreparedStatement ps = con.prepareStatement(DocumentationSQLConstant.INSERT_DOCUMENTATION_CONENT_SQL);
				ps.setBlob(1, in);
				ps.setString(2, agency);
				ps.setString(3, fileKey);
				return ps;
			}
		});
	}

	@Override
	public void deleteDocumentation(String agency, String fileKey) throws DAOException
	{
		dbAccessor.execute(DocumentationSQLConstant.DELETE_DOCUMENTATION_SQL, new Object[] {agency, fileKey});
	}

	@Override
	public InputStream getDocumentContent(String agency, String fileKey) throws DAOException
	{
		List<InputStream> result = dbAccessor.search(DocumentationSQLConstant.GET_DOCUMENTATION_CONTENT_SQL,
			new Object[] {agency, fileKey}, documentationInputProcessor());
		if (result.size() > 0)
		{
			return result.get(0);
		}
		return null;
	}

	@Override
	public void updateDocumentation(String agency, String fileKey, InputStream in) throws DAOException
	{
		dbAccessor.execute(DocumentationSQLConstant.INSERT_DOCUMENTATION_CONENT_SQL, new Object[] {agency, fileKey});
		OutputStream out = dbAccessor.search(DocumentationSQLConstant.GET_DOCUMENTATION_CONTENT_SQL,
			new Object[] {agency, fileKey}, documentationOutputProcessor()).get(0);
		if (out == null)
		{
			throw new DAOException("Initial BLOB fail for file content field.");
		}
		byte[] temp = new byte[512];
		int characters;
		try
		{
			while ((characters = in.read(temp)) != -1)
			{
				out.write(temp, 0, characters);
			}
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public DocumentationModel getDocumentModel(String agency, String fileKey) throws DAOException
	{
		List<DocumentationModel> result = dbAccessor.search(DocumentationSQLConstant.GET_DOCUMENTATION_SQL,
			new Object[] {agency, fileKey}, getDocmentDationModelProcessor());
		if (result.size() == 1)
		{
			return result.get(0);
		}
		return null;
	}
}