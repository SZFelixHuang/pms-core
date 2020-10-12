package com.pms.framework.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 
 *  Accela Automation
 *  File: SQLKeyWords.java
 * 
 *  Accela, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 * 	$Id: SQLKeyWords.java 72642 2009-01-01 20:01:57Z Administrator $ 
 * 
 *  Revision History
 *  Aug 24, 2016		Administrator		Initial.
 * 
 * </pre>
 */
public class SQLKeyWords
{
	public static String sqlRegex = "";

	public static final List<String> sqlKeyWord = new ArrayList<String>()
	{
		/**
		 * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
		 */
		private static final long serialVersionUID = 1L;

		{
			add("ABORT");
			add("ABS");
			add("ABSOLUTE");
			add("ACCESS");
			add("ACTION");
			add("ADA");
			add("ADD");
			add("ADMIN");
			add("AFTER");
			add("AGGREGATE");
			add("ALIAS");
			add("ALL");
			add("ALLOCATE");
			add("ALTER");
			add("ANALYSE");
			add("ANALYZE");
			add("AND");
			add("ANY");
			add("ARE");
			add("ARRAY");
			add("AS");
			add("ASC");
			add("ASENSITIVE");
			add("ASSERTION");
			add("ASSIGNMENT");
			add("ASYMMETRIC");
			add("AT");
			add("ATOMIC");
			add("AUTHORIZATION");
			add("AVG");
			add("BACKWARD");
			add("BEFORE");
			add("BEGIN");
			add("BETWEEN");
			add("BINARY");
			add("BIT");
			add("BITVAR");
			add("BIT_LENGTH");
			add("BLOB");
			add("BOOLEAN");
			add("BOTH");
			add("BREADTH");
			add("BY");
			add("CACHE");
			add("CALL");
			add("CALLED");
			add("CARDINALITY");
			add("CASCADE");
			add("CASCADED");
			add("CASE");
			add("CAST");
			add("CATALOG");
			add("CATALOG_NAME");
			add("CHAIN");
			add("CHAR");
			add("CHARACTER");
			add("CHARACTERISTICS");
			add("CHARACTER_LENGTH");
			add("CHARACTER_SET_CATALOG");
			add("CHARACTER_SET_NAME");
			add("CHARACTER_SET_SCHEMA");
			add("CHAR_LENGTH");
			add("CHECK");
			add("CHECKED");
			add("CHECKPOINT");
			add("CLASS");
			add("CLASS_ORIGIN");
			add("CLOB");
			add("CLOSE");
			add("CLUSTER");
			add("COALESCE");
			add("COBOL");
			add("COLLATE");
			add("COLLATION");
			add("COLLATION_CATALOG");
			add("COLLATION_NAME");
			add("COLLATION_SCHEMA");
			add("COLUMN");
			add("COLUMN_NAME");
			add("COMMAND_FUNCTION");
			add("COMMAND_FUNCTION_CODE");
			add("COMMENT");
			add("COMMIT");
			add("COMMITTED");
			add("COMPLETION");
			add("CONDITION_NUMBER");
			add("CONNECT");
			add("CONNECTION");
			add("CONNECTION_NAME");
			add("CONSTRAINT");
			add("CONSTRAINTS");
			add("CONSTRAINT_CATALOG");
			add("CONSTRAINT_NAME");
			add("CONSTRAINT_SCHEMA");
			add("CONSTRUCTOR");
			add("CONTAINS");
			add("CONTINUE");
			add("CONVERT");
			add("COPY");
			add("CORRESPONDING");
			add("COUNT");
			add("CREATE");
			add("CREATEDB");
			add("CREATEUSER");
			add("CROSS");
			add("CUBE");
			add("CURRENT");
			add("CURRENT_DATE");
			add("CURRENT_PATH");
			add("CURRENT_ROLE");
			add("CURRENT_TIME");
			add("CURRENT_TIMESTAMP");
			add("CURRENT_USER");
			add("CURSOR");
			add("CURSOR_NAME");
			add("CYCLE");
			add("DATA");
			add("DATABASE");
			add("DATE");
			add("DATETIME_INTERVAL_CODE");
			add("DATETIME_INTERVAL_PRECISION");
			add("DAY");
			add("DEALLOCATE");
			add("DEC");
			add("DECIMAL");
			add("DECLARE");
			add("DEFAULT");
			add("DEFERRABLE");
			add("DEFERRED");
			add("DEFINED");
			add("DEFINER");
			add("DELETE");
			add("DELIMITERS");
			add("DEPTH");
			add("DEREF");
			add("DESC");
			add("DESCRIBE");
			add("DESCRIPTOR");
			add("DESTROY");
			add("DESTRUCTOR");
			add("DETERMINISTIC");
			add("DIAGNOSTICS");
			add("DICTIONARY");
			add("DISCONNECT");
			add("DISPATCH");
			add("DISTINCT");
			add("DO");
			add("DOMAIN");
			add("DOUBLE");
			add("DROP");
			add("DYNAMIC");
			add("DYNAMIC_FUNCTION");
			add("DYNAMIC_FUNCTION_CODE");
			add("EACH");
			add("ELSE");
			add("ENCODING");
			add("ENCRYPTED");
			add("END");
			add("END-EXEC");
			add("EQUALS");
			add("ESCAPE");
			add("EVERY");
			add("EXCEPT");
			add("EXCEPTION");
			add("EXCLUSIVE");
			add("EXEC");
			add("EXECUTE");
			add("EXISTING");
			add("EXISTS");
			add("EXPLAIN");
			add("EXTERNAL");
			add("EXTRACT");
			add("FALSE");
			add("FETCH");
			add("FINAL");
			add("FIRST");
			add("FLOAT");
			add("FOR");
			add("FORCE");
			add("FOREIGN");
			add("FORTRAN");
			add("FORWARD");
			add("FOUND");
			add("FREE");
			add("FREEZE");
			add("FROM");
			add("FULL");
			add("FUNCTION");
			add("GENERAL");
			add("GENERATED");
			add("GET");
			add("GLOBAL");
			add("GO");
			add("GOTO");
			add("GRANT");
			add("GRANTED");
			add("GROUP");
			add("GROUPING");
			add("HANDLER");
			add("HAVING");
			add("HIERARCHY");
			add("HOLD");
			add("HOST");
			add("HOUR");
			add("IDENTITY");
			add("IGNORE");
			add("ILIKE");
			add("IMMEDIATE");
			add("IMPLEMENTATION");
			add("IN");
			add("INCREMENT");
			add("INDEX");
			add("INDICATOR");
			add("INFIX");
			add("INHERITS");
			add("INITIALIZE");
			add("INITIALLY");
			add("INNER");
			add("INOUT");
			add("INPUT");
			add("INSENSITIVE");
			add("INSERT");
			add("INSTANCE");
			add("INSTANTIABLE");
			add("INSTEAD");
			add("INT");
			add("INTEGER");
			add("INTERSECT");
			add("INTERVAL");
			add("INTO");
			add("INVOKER");
			add("IS");
			add("ISNULL");
			add("ISOLATION");
			add("ITERATE");
			add("JOIN");
			add("KEY");
			add("KEY_MEMBER");
			add("KEY_TYPE");
			add("LANCOMPILER");
			add("LANGUAGE");
			add("LARGE");
			add("LAST");
			add("LATERAL");
			add("LEADING");
			add("LEFT");
			add("LENGTH");
			add("LESS");
			add("LEVEL");
			add("LIKE");
			add("LIMIT");
			add("LISTEN");
			add("LOAD");
			add("LOCAL");
			add("LOCALTIME");
			add("LOCALTIMESTAMP");
			add("LOCATION");
			add("LOCATOR");
			add("LOCK");
			add("LOWER");
			add("MAP");
			add("MATCH");
			add("MAX");
			add("MAXVALUE");
			add("MESSAGE_LENGTH");
			add("MESSAGE_OCTET_LENGTH");
			add("MESSAGE_TEXT");
			add("METHOD");
			add("MIN");
			add("MINUTE");
			add("MINVALUE");
			add("MOD");
			add("MODE");
			add("MODIFIES");
			add("MODIFY");
			add("MODULE");
			add("MONTH");
			add("MORE");
			add("MOVE");
			add("MUMPS");
			add("NAME");
			add("NAMES");
			add("NATIONAL");
			add("NATURAL");
			add("NCHAR");
			add("NCLOB");
			add("NEW");
			add("NEXT");
			add("NO");
			add("NOCREATEDB");
			add("NOCREATEUSER");
			add("NONE");
			add("NOT");
			add("NOTHING");
			add("NOTIFY");
			add("NOTNULL");
			add("NULL");
			add("NULLABLE");
			add("NULLIF");
			add("NUMBER");
			add("NUMERIC");
			add("OBJECT");
			add("OCTET_LENGTH");
			add("OF");
			add("OFF");
			add("OFFSET");
			add("OIDS");
			add("OLD");
			add("ON");
			add("ONLY");
			add("OPEN");
			add("OPERATION");
			add("OPERATOR");
			add("OPTION");
			add("OPTIONS");
			add("OR");
			add("ORDER");
			add("ORDINALITY");
			add("OUT");
			add("OUTER");
			add("OUTPUT");
			add("OVERLAPS");
			add("OVERLAY");
			add("OVERRIDING");
			add("OWNER");
			add("PAD");
			add("PARAMETER");
			add("PARAMETERS");
			add("PARAMETER_MODE");
			add("PARAMETER_NAME");
			add("PARAMETER_ORDINAL_POSITION");
			add("PARAMETER_SPECIFIC_CATALOG");
			add("PARAMETER_SPECIFIC_NAME");
			add("PARAMETER_SPECIFIC_SCHEMA");
			add("PARTIAL");
			add("PASCAL");
			add("PASSWORD");
			add("PATH");
			add("PENDANT");
			add("PLI");
			add("POSITION");
			add("POSTFIX");
			add("PRECISION");
			add("PREFIX");
			add("PREORDER");
			add("PREPARE");
			add("PRESERVE");
			add("PRIMARY");
			add("PRIOR");
			add("PRIVILEGES");
			add("PROCEDURAL");
			add("PROCEDURE");
			add("PUBLIC");
			add("READ");
			add("READS");
			add("REAL");
			add("RECURSIVE");
			add("REF");
			add("REFERENCES");
			add("REFERENCING");
			add("REINDEX");
			add("RELATIVE");
			add("RENAME");
			add("REPEATABLE");
			add("REPLACE");
			add("RESET");
			add("RESTRICT");
			add("RESULT");
			add("RETURN");
			add("RETURNED_LENGTH");
			add("RETURNED_OCTET_LENGTH");
			add("RETURNED_SQLSTATE");
			add("RETURNS");
			add("REVOKE");
			add("RIGHT");
			add("ROLE");
			add("ROLLBACK");
			add("ROLLUP");
			add("ROUTINE");
			add("ROUTINE_CATALOG");
			add("ROUTINE_NAME");
			add("ROUTINE_SCHEMA");
			add("ROW");
			add("ROWS");
			add("ROW_COUNT");
			add("RULE");
			add("SAVEPOINT");
			add("SCALE");
			add("SCHEMA");
			add("SCHEMA_NAME");
			add("SCOPE");
			add("SCROLL");
			add("SEARCH");
			add("SECOND");
			add("SECTION");
			add("SECURITY");
			add("SELECT");
			add("SELF");
			add("SENSITIVE");
			add("SEQUENCE");
			add("SERIALIZABLE");
			add("SERVER_NAME");
			add("SESSION");
			add("SESSION_USER");
			add("SET");
			add("SETOF");
			add("SETS");
			add("SHARE");
			add("SHOW");
			add("SIMILAR");
			add("SIMPLE");
			add("SIZE");
			add("SMALLINT");
			add("SOME");
			add("SOURCE");
			add("SPACE");
			add("SPECIFIC");
			add("SPECIFICTYPE");
			add("SPECIFIC_NAME");
			add("SQL");
			add("SQLCODE");
			add("SQLERROR");
			add("SQLEXCEPTION");
			add("SQLSTATE");
			add("SQLWARNING");
			add("START");
			add("STATE");
			add("STATEMENT");
			add("STATIC");
			add("STATISTICS");
			add("STDIN");
			add("STDOUT");
			add("STRUCTURE");
			add("STYLE");
			add("SUBCLASS_ORIGIN");
			add("SUBLIST");
			add("SUBSTRING");
			add("SUM");
			add("SYMMETRIC");
			add("SYSID");
			add("SYSTEM");
			add("SYSTEM_USER");
			add("TABLE");
			add("TABLE_NAME");
			add("TEMP");
			add("TEMPLATE");
			add("TEMPORARY");
			add("TERMINATE");
			add("THAN");
			add("THEN");
			add("TIME");
			add("TIMESTAMP");
			add("TIMEZONE_HOUR");
			add("TIMEZONE_MINUTE");
			add("TO");
			add("TOAST");
			add("TRAILING");
			add("TRANSACTION");
			add("TRANSACTIONS_COMMITTED");
			add("TRANSACTIONS_ROLLED_BACK");
			add("TRANSACTION_ACTIVE");
			add("TRANSFORM");
			add("TRANSFORMS");
			add("TRANSLATE");
			add("TRANSLATION");
			add("TREAT");
			add("TRIGGER");
			add("TRIGGER_CATALOG");
			add("TRIGGER_NAME");
			add("TRIGGER_SCHEMA");
			add("TRIM");
			add("TRUE");
			add("TRUNCATE");
			add("TRUSTED");
			add("TYPE");
			add("UNCOMMITTED");
			add("UNDER");
			add("UNENCRYPTED");
			add("UNION");
			add("UNIQUE");
			add("UNKNOWN");
			add("UNLISTEN");
			add("UNNAMED");
			add("UNNEST");
			add("UNTIL");
			add("UPDATE");
			add("UPPER");
			add("USAGE");
			add("USER");
			add("USER_DEFINED_TYPE_CATALOG");
			add("USER_DEFINED_TYPE_NAME");
			add("USER_DEFINED_TYPE_SCHEMA");
			add("USING");
			add("VACUUM");
			add("VALID");
			add("VALUE");
			add("VALUES");
			add("VARCHAR");
			add("VARIABLE");
			add("VARYING");
			add("VERBOSE");
			add("VERSION");
			add("VIEW");
			add("WHEN");
			add("WHENEVER");
			add("WHERE");
			add("WITH");
			add("WITHOUT");
			add("WORK");
			add("WRITE");
			add("YEAR");
			add("ZONE");
		}
	};

	static
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (int index = 0; index < sqlKeyWord.size(); index++)
		{
			stringBuilder.append("(");
			stringBuilder.append(sqlKeyWord.get(index));
			stringBuilder.append(" )|");
		}
		sqlRegex = stringBuilder.substring(0, stringBuilder.length() - 1);
	}

	// public static void main(String[] aa)
	// {
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:mmm");
	// System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
	// String SELECT_CAPDETAIL = "select distinct serv_prov_code, b1_per_id1, b1_per_id2, b1_per_id3, app_status, "
	// + " app_status_date, disposition, disposition_date, total_fee, total_pay, percent_complete, "
	// + " balance, balance_date, house_count, building_count, public_owned, const_type_code, "
	// + " action,ga_agency_code,ga_bureau_code,ga_division_code,ga_office_code, "
	// + " ga_section_code,ga_group_code,ga_fname,ga_mname,ga_lname,rec_date,rec_ful_nam,rec_status, "
	// + " b1_appl_sub_status,b1_short_notes,b1_closed_date,b1_closedby,b1_reported_channel,"
	// + " b1_asgn_dept,b1_asgn_staff,b1_priority,b1_severity,b1_asgn_date,total_job_cost ,"
	// + " b1_closed_dept,b1_complete_by,b1_complete_dept,b1_complete_date,b1_scheduled_date,pm_schedule_seq,"
	// + " b1_est_prod_units, b1_actual_prod_units, b1_est_cost_per_unit, b1_cost_per_unit, b1_est_job_cost,"
	// + " b1_prod_unit_type, b1_creator_dept,b1_created_by as b1createdby,'' as r3_deptname,"
	// + " b1_track_start_date, b1_estimated_due_date, b1_in_possession_time, b1_overall_application_time,"
	// + " c6_anonymous_flag, c6_reference_type, c6_appearance_dayofweek, c6_appearance_dd, "
	// + " c6_booking_flag, c6_dfndt_signature_flag, c6_enforce_officer_id, c6_enforce_officer_name,"
	// + " c6_infraction_flag, c6_inspector_id, c6_misdemeanor_flag, c6_offn_witnessed_flag,"
	// + " c6_inspector_name, c6_enforce_dept, c6_inspector_dept,"
	// + " app_status_reason, first_issued_date, undistributed_job_cost, b1_val_multiplier, b1_val_extra_amt, url,
	// generated_by_cloning "
	// + " from (select * from aa where aa) ";
	// Pattern p = Pattern.compile(sqlRegex, Pattern.CASE_INSENSITIVE);
	// Matcher m = p.matcher(SELECT_CAPDETAIL);
	// String keyWord;
	// while (m.find())
	// {
	// keyWord = m.group(0);
	// SELECT_CAPDETAIL = SELECT_CAPDETAIL.replace(keyWord, keyWord.toUpperCase());
	// }
	// System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
	// System.out.println(new SQLFormatter(SELECT_CAPDETAIL).format());
	// }
}
/*
 * $Log: av-env.bat,v $
 */