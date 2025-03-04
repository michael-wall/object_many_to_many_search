package com.mw.portal.search.object;

public class MWConstants {
	
	public static final String STUDENT_OBJECT_ENTRY_CLASS_NAME = "com.liferay.object.model.ObjectDefinition#G9X2";
	
	//public static final long STUDENT_OBJECT_DEFINITION_ID = 34503;
	public static final long MODULE_OBJECT_DEFINITION_ID = 34552;
		
	public static final long MANY_TO_MANY_RELATIONSHIP_ID = 34614; // Used when getting Modules for a known Student.
	public static final long MANY_TO_MANY_REVERSE_RELATIONSHIP_ID = 34615; // User when getting Students for a known Module.
	
	public interface STUDENT_CUSTOM_SEARCH_FIELDS {
		public static final String MODULE_NAMES = "moduleNames";
		public static final String MODULE_CODES = "moduleCodes";
		public static final String MODULE_LECTURERS = "moduleLecturers";
		public static final String MODULE_DEPARTMENTS = "moduleDepartments";
		public static final String MODULE_CREDITS = "moduleCredits";
		
		public static final String MODULES_ONLINE_ONLY = "modulesOnlineOnly";
		public static final String MODULE_COUNT = "moduleCount";
		public static final String MODULE_IDS = "moduleIds";
	}	
	
	public interface MODULE_OBJECT_FIELDS {
		public static final String MODULE_NAME = "moduleName";
		public static final String MODULE_CODE = "moduleCountry";
		public static final String MODULE_LECTURER = "moduleLecturer";
		public static final String MODULE_DEPARTMENT = "moduleDepartment";
		public static final String MODULE_CREDITS = "moduleCredits";
		public static final String MODULE_ONLINE = "moduleOnline";
	}
}