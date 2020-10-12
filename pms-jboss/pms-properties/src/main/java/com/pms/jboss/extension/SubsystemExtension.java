package com.pms.jboss.extension;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;

import org.jboss.as.controller.Extension;
import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.descriptions.StandardResourceDescriptionResolver;
import org.jboss.as.controller.operations.common.GenericSubsystemDescribeHandler;
import org.jboss.as.controller.parsing.ExtensionParsingContext;
import org.jboss.as.controller.parsing.ParseUtils;
import org.jboss.as.controller.persistence.SubsystemMarshallingContext;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelNode;
import org.jboss.staxmapper.XMLElementReader;
import org.jboss.staxmapper.XMLElementWriter;
import org.jboss.staxmapper.XMLExtendedStreamReader;
import org.jboss.staxmapper.XMLExtendedStreamWriter;

/**
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
public class SubsystemExtension implements Extension
{

	/**
	 * The name space used for the {@code substystem} element
	 */
	public static final String NAMESPACE = "urn:com.pms.extensions:1.0";

	/**
	 * The name of our subsystem within the model.
	 */
	public static final String SUBSYSTEM_NAME = "PMS_Properties_Extention";

	private static String serverConfigFile = "ServerConfig.properties";

	private static String PMS_AS_HOME = "pms_home_dir";

	private static String PMS_AS_CONF_DIR = "pms_conf_dir";

	/**
	 * The parser used for parsing our subsystem
	 */
	private final SubsystemParser parser = new SubsystemParser();

	protected static final PathElement SUBSYSTEM_PATH = PathElement.pathElement(SUBSYSTEM, SUBSYSTEM_NAME);

	private static final String RESOURCE_NAME = SubsystemExtension.class.getPackage().getName() + ".LocalDescriptions";

	static StandardResourceDescriptionResolver getResourceDescriptionResolver(final String keyPrefix)
	{
		String prefix = SUBSYSTEM_NAME + (keyPrefix == null ? "" : "." + keyPrefix);
		return new StandardResourceDescriptionResolver(prefix, RESOURCE_NAME, SubsystemExtension.class.getClassLoader(),
				true, false);
	}

	@Override
	public void initializeParsers(ExtensionParsingContext context)
	{
		context.setSubsystemXmlMapping(SUBSYSTEM_NAME, NAMESPACE, parser);
	}

	@Override
	public void initialize(ExtensionContext context)
	{
		final SubsystemRegistration subsystem = context.registerSubsystem(SUBSYSTEM_NAME, 1, 0);
		final ManagementResourceRegistration registration = subsystem
				.registerSubsystemModel(SubsystemDefinition.INSTANCE);
		registration.registerOperationHandler(GenericSubsystemDescribeHandler.DEFINITION,
			GenericSubsystemDescribeHandler.INSTANCE);

		subsystem.registerXMLElementWriter(parser);
		getAndInsertProperties();
	}

	private static ModelNode createAddSubsystemOperation()
	{
		final ModelNode subsystem = new ModelNode();
		subsystem.get(OP).set(ADD);
		subsystem.get(OP_ADDR).add(SUBSYSTEM, SUBSYSTEM_NAME);
		return subsystem;
	}

	/**
	 * The subsystem parser, which uses stax to read and write to and from xml
	 */
	private static class SubsystemParser implements XMLStreamConstants, XMLElementReader<List<ModelNode>>,
			XMLElementWriter<SubsystemMarshallingContext>
	{

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void writeContent(XMLExtendedStreamWriter writer, SubsystemMarshallingContext context)
				throws XMLStreamException
		{
			context.startSubsystemElement(SubsystemExtension.NAMESPACE, false);
			writer.writeEndElement();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void readElement(XMLExtendedStreamReader reader, List<ModelNode> list) throws XMLStreamException
		{
			// Require no content
			ParseUtils.requireNoContent(reader);
			list.add(createAddSubsystemOperation());
		}
	}

	private void getAndInsertProperties()
	{
		System.out.println("Start PropertiesExtension");
		String serverBaseDir = System.getProperty(PMS_AS_HOME);
		if (serverBaseDir == null)
		{
			serverBaseDir = System.getenv(PMS_AS_HOME);
			if (serverBaseDir == null)
			{
				throw new RuntimeException("PMSProperties: Cannot find PMS AS Home PMS_AS_HOME");
			}
		}
		serverBaseDir = serverBaseDir.replace("\\", "/");
		System.setProperty(PMS_AS_HOME, serverBaseDir);

		String serverConDir = System.getProperty(PMS_AS_CONF_DIR);
		if (serverConDir == null)
		{
			serverConDir = System.getenv(PMS_AS_CONF_DIR);
			if (serverConDir == null)
			{
				throw new RuntimeException("PMSProperties: Cannot find PMS AS configuration directory PMS_AS_CONF_DIR");
			}
		}
		serverConDir = serverConDir.replace("\\", "/");
		System.setProperty(PMS_AS_CONF_DIR, serverConDir);

		System.out.println(PMS_AS_HOME + ":" + serverBaseDir);
		System.out.println(PMS_AS_CONF_DIR + ":" + serverConDir);
		Reader inStream = null;
		try
		{
			Properties props = new Properties();
			inStream = new FileReader(serverBaseDir + "/" + serverConDir + "/" + serverConfigFile);
			props.load(inStream);
			Properties properties = System.getProperties();
			for (Entry entry : props.entrySet())
			{
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				properties.put(key, value);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				inStream.close();
			}
			catch (Exception localException1)
			{
			}
		}
		finally
		{
			try
			{
				inStream.close();
			}
			catch (Exception localException2)
			{
			}
		}
	}
}