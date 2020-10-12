package com.pms.commons.ftp;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectState;

import com.pms.commons.exception.SystemException;

public class FTPClientPool implements ObjectPool<PooledObject<FTPClient>>
{
	private final BlockingQueue<PooledObject<FTPClient>> pool;

	private final FTPClientFactory factory;

	public FTPClientPool(FtpClientConfiguration ftpClientConfig) throws Exception
	{
		this.factory = new FTPClientFactory(ftpClientConfig);
		pool = new ArrayBlockingQueue<PooledObject<FTPClient>>(ftpClientConfig.getPoolSize() * 2);
		initPool(ftpClientConfig.getPoolSize());
	}

	private void initPool(int maxPoolSize) throws Exception
	{
		for (int i = 0; i < maxPoolSize; i++)
		{
			addObject();
		}
	}

	@Override
	public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException
	{
		pool.offer(factory.makeObject(), 3, TimeUnit.SECONDS);
	}

	@Override
	public PooledObject<FTPClient> borrowObject() throws Exception, NoSuchElementException, IllegalStateException
	{
		if (!pool.isEmpty() && pool.size() > this.factory.getFtpClientConfiguration().getPoolSize())
		{
			this.clear();
		}
		PooledObject<FTPClient> pooledObject;
		if (pool.isEmpty())
		{
			pooledObject = factory.makeObject();
			addObject();
		}
		else
		{
			pooledObject = pool.take();
			if (pooledObject == null)
			{
				pooledObject = factory.makeObject();
				addObject();
			}
			else if (!factory.validateObject(pooledObject))
			{
				invalidateObject(pooledObject);
				pooledObject = factory.makeObject();
				addObject();
			}
		}
		return pooledObject;
	}

	@Override
	public void clear() throws Exception, UnsupportedOperationException
	{
		Iterator<PooledObject<FTPClient>> iterator = this.pool.iterator();
		int loop = this.pool.size() - this.factory.getFtpClientConfiguration().getPoolSize();
		if (loop > 0)
		{
			while (iterator.hasNext())
			{
				PooledObject<FTPClient> pooledObject = iterator.next();
				if (pooledObject.getState() == PooledObjectState.IDLE)
				{
					this.invalidateObject(pooledObject);
					loop--;
					if (loop == 0)
					{
						break;
					}
				}
			}
		}
	}

	@Override
	public void close()
	{
		while (pool.iterator().hasNext())
		{
			PooledObject<FTPClient> pooledObject;
			try
			{
				pooledObject = pool.take();
				factory.destroyObject(pooledObject);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getNumActive()
	{
		return 0;
	}

	@Override
	public int getNumIdle()
	{
		return 0;
	}

	@Override
	public void invalidateObject(PooledObject<FTPClient> pooledObject) throws Exception
	{
		FTPClient ftpClient = pooledObject.getObject();
		if (ftpClient.isConnected())
		{
			if (ftpClient.isAvailable())
			{
				try
				{
					ftpClient.logout();
				}
				catch (IOException e)
				{

				}
			}
			pooledObject.getObject().disconnect();
		}
		pool.remove(pooledObject);
	}

	@Override
	public void returnObject(PooledObject<FTPClient> pooledObject) throws SystemException
	{
		try
		{
			if ((pooledObject != null) && !pool.offer(pooledObject, 3, TimeUnit.SECONDS))
			{
				factory.destroyObject(pooledObject);
			}
		}
		catch (Exception e)
		{
			throw new SystemException(e.getMessage());
		}
	}
}