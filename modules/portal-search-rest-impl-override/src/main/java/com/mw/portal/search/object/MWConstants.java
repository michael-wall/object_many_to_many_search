package com.mw.portal.search.object;

public class MWConstants {
	
	public static final String STUDENT_OBJECT_ENTRY_CLASS_NAME = "com.liferay.object.model.ObjectDefinition#G9X2";
	
	public static final long GROUP_ID = 0; // When Entity is 'company' scoped the groupId is 0, shouldn't need to change.
	
	public static final long MODULE_OBJECT_DEFINITION_ID = 34552;
		
	public static final long MANY_TO_MANY_RELATIONSHIP_ID = 34614; // Used when getting Modules for a known Student.
	public static final long MANY_TO_MANY_REVERSE_RELATIONSHIP_ID = 34615; // Used when getting Students for a known Module.
}