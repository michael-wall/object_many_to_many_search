package com.mw.portal.search.object;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Wall
 */
@Component(
		property = "indexer.class.name=" + MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME,
		service = ModelDocumentContributor.class
	)
	public class StudentObjectEntryDocumentContributor
		implements ModelDocumentContributor<ObjectEntry> {

	@Override
	public void contribute(Document document, ObjectEntry objectEntry) {		
		try {
			List<ObjectEntry> relatedObjectEntries = objectEntryLocalService.getManyToManyObjectEntries(objectEntry.getGroupId(), MWConstants.MANY_TO_MANY_RELATIONSHIP_ID, objectEntry.getObjectEntryId(), true, false, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			
			long mappedCount = relatedObjectEntries.size();
			long credits = 0;
			boolean onlineOnly = true;
			String mappedIds = "";
			
			String moduleNamesCombined = "";
			String moduleCodesCombined = "";
			String moduleLecturersDedupCombined = "";
			String moduleDepartmentsDedupCombined = "";
			Map<String, String> lecturersMap = new HashMap<String, String>(); 
			Map<String, String> departmentsMap = new HashMap<String, String>(); 
			
			_log.info("objectEntryId: " + objectEntry.getObjectEntryId() + ", model: " + objectEntry.getModelClassName() + ", ManyToMany records: " + relatedObjectEntries.size());
			
			for (ObjectEntry relatedObjectEntry: relatedObjectEntries) {
				
				if (Validator.isNotNull(mappedIds)) mappedIds += ",";
				mappedIds += relatedObjectEntry.getObjectEntryId() + "";
				
				Map<String, Serializable> relatedObjectEntryValues = objectEntryLocalService.getValues(relatedObjectEntry.getObjectEntryId());

				if (relatedObjectEntryValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_NAME)) {					
					if (Validator.isNotNull(moduleNamesCombined)) moduleNamesCombined += ", ";
					
					moduleNamesCombined += relatedObjectEntryValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_NAME);
				}
				
				if (relatedObjectEntryValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CODE)) { 					
					if (Validator.isNotNull(moduleCodesCombined)) moduleCodesCombined += ", ";
					
					moduleCodesCombined += relatedObjectEntryValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CODE);
				}
				
				if (relatedObjectEntryValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_LECTURER)) {
					String lecturer = (String)relatedObjectEntryValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_LECTURER);
					
					if (!lecturersMap.containsKey(lecturer.toLowerCase())) {
						if (Validator.isNotNull(moduleLecturersDedupCombined)) moduleLecturersDedupCombined += ", ";
						
						moduleLecturersDedupCombined += lecturer;
						
						lecturersMap.put(lecturer.toLowerCase(), lecturer.toLowerCase());
					}
				}
				
				if (relatedObjectEntryValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_DEPARTMENT)) { 
					String department = (String)relatedObjectEntryValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_DEPARTMENT);
					
					if (!departmentsMap.containsKey(department.toLowerCase())) {
						if (Validator.isNotNull(moduleDepartmentsDedupCombined)) moduleDepartmentsDedupCombined += ", ";
						
						moduleDepartmentsDedupCombined += department;
						
						departmentsMap.put(department.toLowerCase(), department.toLowerCase());
					}
				}
				
				if (relatedObjectEntryValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CREDITS)) {
					Integer moduleCredits = (Integer)relatedObjectEntryValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CREDITS);

					credits += Long.valueOf(moduleCredits);
				}
				
				if (relatedObjectEntryValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_ONLINE)) {	
					Boolean moduleOnline = (Boolean)relatedObjectEntryValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_ONLINE);

					if (!moduleOnline) onlineOnly = false;
				}
			}

			// Update the fields even if relatedObjectEntries is 0 (in case a mapping was removed etc.)
			
			document.addKeyword(MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_NAMES, moduleNamesCombined); 
			document.addKeyword(MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_CODES, moduleCodesCombined); 
			document.addKeyword(MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_LECTURERS, moduleLecturersDedupCombined); 
			document.addKeyword(MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_DEPARTMENTS, moduleDepartmentsDedupCombined); 
			
			document.addKeyword(MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_CREDITS, credits); 
			document.addKeyword(MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULES_ONLINE_ONLY, onlineOnly); 
			
			document.addKeyword(MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_COUNT, mappedCount); 
			document.addKeyword(MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_IDS, mappedIds); 
					
			_log.info("Added or updated fields on Student " + objectEntry.getObjectEntryId() + " Elasticsearch document.");

		} catch (PortalException e) {
			e.printStackTrace();
		}
	}
	
	@Reference(unbind = "-")
	private ObjectEntryLocalService objectEntryLocalService;
	
	private static Log _log = LogFactoryUtil.getLog(StudentObjectEntryDocumentContributor.class);
}