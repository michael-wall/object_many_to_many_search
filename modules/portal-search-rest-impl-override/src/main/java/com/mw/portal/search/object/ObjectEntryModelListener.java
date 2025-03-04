package com.mw.portal.search.object;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Wall
 */
@Component(service = ModelListener.class)
public class ObjectEntryModelListener extends BaseModelListener<ObjectEntry> {
	
	private boolean isTrackedFieldChanged(ObjectEntry originalModel, ObjectEntry model) {
		
		// Check only the fields that we are indexing into Student...
		
		// The logic here could be cleaner by passing an array of field names for example...
		
		Map<String, Serializable> originalValues = originalModel.getValues();
		Map<String, Serializable> newValues = model.getValues();
		
		String originalModuleName = "";
		String newModuleName = "";
		
		if (originalValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_NAME)) originalModuleName = (String)originalValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_NAME);
		if (newValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_NAME)) newModuleName = (String)newValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_NAME);
		
		if (!originalModuleName.equalsIgnoreCase(newModuleName)) return true;
		
		String originalModuleCode = "";
		String newModuleCode = "";
		
		if (originalValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CODE)) originalModuleCode = (String)originalValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CODE);
		if (newValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CODE)) newModuleCode = (String)newValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CODE);
		
		if (!originalModuleCode.equalsIgnoreCase(newModuleCode)) return true;
		
		String originalModuleLecturer = "";
		String newModuleLecturer = "";
		
		if (originalValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_LECTURER)) originalModuleLecturer = (String)originalValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_LECTURER);
		if (newValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_LECTURER)) newModuleLecturer = (String)newValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_LECTURER);
		
		if (!originalModuleLecturer.equalsIgnoreCase(newModuleLecturer)) return true;
		
		String originalModuleDepartment = "";
		String newModuleDepartment = "";
		
		if (originalValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_DEPARTMENT)) originalModuleDepartment = (String)originalValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_DEPARTMENT);
		if (newValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_DEPARTMENT)) newModuleDepartment = (String)newValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_DEPARTMENT);
		
		if (!originalModuleDepartment.equalsIgnoreCase(newModuleDepartment)) return true;
		
		Integer originalCredits = 0;
		Integer newCredits = 0;
		
		if (originalValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CREDITS)) originalCredits = (Integer)originalValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CREDITS);
		if (newValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CREDITS)) newCredits = (Integer)newValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_CREDITS);
		
		if (originalCredits != newCredits) return true;
		
		Boolean originalOnline = false;
		Boolean newOnline = false;

		if (originalValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_ONLINE)) originalOnline = (Boolean)originalValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_ONLINE);
		if (newValues.containsKey(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_ONLINE)) newOnline = (Boolean)newValues.get(MWFieldConstants.MODULE_OBJECT_FIELDS.MODULE_ONLINE);
		
		if (originalOnline != newOnline) return true;
		
		return false;
	}
	
	@Override
	public void onAfterUpdate(ObjectEntry originalModel, ObjectEntry model) throws ModelListenerException {

		if (originalModel.getObjectDefinitionId() != MWConstants.MODULE_OBJECT_DEFINITION_ID) {
			super.onAfterUpdate(originalModel, model);
			
			return;
		}
		
		_log.info("onAfterUpdate for ID: " + originalModel.getObjectEntryId());

		boolean isTrackedFieldChanged = isTrackedFieldChanged(originalModel, model);
		
		_log.info("isTrackedFieldChanged: " + isTrackedFieldChanged);
		
		if (!isTrackedFieldChanged) {	
			super.onAfterUpdate(originalModel, model);
			
			return;
		}

		Indexer<ObjectEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME);
		
		try {
			List<ObjectEntry> relatedObjectEntries = objectEntryLocalService.getManyToManyObjectEntries(originalModel.getGroupId(), MWConstants.MANY_TO_MANY_REVERSE_RELATIONSHIP_ID, originalModel.getObjectEntryId(), true, true, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			
			_log.debug("Mapped count: " + relatedObjectEntries.size());
			
			for (ObjectEntry relatedObjectEntry: relatedObjectEntries) {
				_log.debug("Mapped: " + relatedObjectEntry.getObjectEntryId() + ", " + relatedObjectEntry.getModelClassName());
			
				indexer.reindex(relatedObjectEntry);
			}
		} catch (Exception e) {
			_log.error(e.getStackTrace());
		}
		
		super.onAfterUpdate(originalModel, model);
	}
	
	@Reference(unbind = "-")
	private ObjectEntryLocalService objectEntryLocalService;
	
	@Reference(unbind = "-")
	private ObjectRelationshipLocalService objectRelationshipLocalService;
	
	private static Log _log = LogFactoryUtil.getLog(ObjectEntryModelListener.class);	
}