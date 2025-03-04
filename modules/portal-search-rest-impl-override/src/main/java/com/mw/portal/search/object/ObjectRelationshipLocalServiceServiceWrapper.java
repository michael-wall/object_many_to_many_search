package com.mw.portal.search.object;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectRelationshipLocalServiceWrapper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ServiceWrapper.class)
public class ObjectRelationshipLocalServiceServiceWrapper extends ObjectRelationshipLocalServiceWrapper {

	
	@Override
	public void addObjectRelationshipMappingTableValues(
			long userId, long objectRelationshipId, long primaryKey1,
			long primaryKey2, ServiceContext serviceContext) throws PortalException {
		
		//Called when mapping added from Students Layout.
		_log.info("add objectRelationshipId: " + objectRelationshipId + ", primaryKey1: " + primaryKey1 + ", primaryKey2: " + primaryKey2);
		
		super.addObjectRelationshipMappingTableValues(userId, objectRelationshipId, primaryKey1, primaryKey2, serviceContext);
		
		if (objectRelationshipId == MWConstants.MANY_TO_MANY_RELATIONSHIP_ID) {
			reindexStudent(objectRelationshipId, primaryKey1);
		}
	}

	@Override
	public void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1) throws PortalException {

		//Called when Module record deleted from Module grid screen.
		// Note the Relationship ID is reverse Many to Many relationship ID...
		// Note the Primary Key is the Module ObjectEntityId NOT the Student one.
		_log.info("delete objectRelationshipId: " + objectRelationshipId + ", primaryKey1: " + primaryKey1);
		
		List<ObjectEntry> relatedObjectEntries = null;
		
		if (objectRelationshipId == MWConstants.MANY_TO_MANY_REVERSE_RELATIONSHIP_ID) {
			ObjectRelationship objectRelationship = objectRelationshipLocalService.fetchObjectRelationship(objectRelationshipId);
						
			relatedObjectEntries = objectEntryLocalService.getManyToManyObjectEntries(MWConstants.GROUP_ID, MWConstants.MANY_TO_MANY_REVERSE_RELATIONSHIP_ID, primaryKey1, true, true, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		
		_log.info("relatedObjectEntries count: " + relatedObjectEntries.size());
	
		super.deleteObjectRelationshipMappingTableValues(objectRelationshipId, primaryKey1);
		
		if (relatedObjectEntries != null && !relatedObjectEntries.isEmpty()) {
			reindexStudents(relatedObjectEntries);	
		}
	}
	
	@Override
	public void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2) throws PortalException {
		
		//Called when mapping removed from Students Layout.
		_log.info("delete objectRelationshipId: " + objectRelationshipId + ", primaryKey1: " + primaryKey1 + ", primaryKey2: " + primaryKey2);
		
		super.deleteObjectRelationshipMappingTableValues(objectRelationshipId, primaryKey1, primaryKey2);
		
		if (objectRelationshipId == MWConstants.MANY_TO_MANY_RELATIONSHIP_ID) {
			reindexStudent(objectRelationshipId, primaryKey1);
		}
	}
	
	private void reindexStudent(long objectRelationshipId, long primaryKey1) {
		Indexer<ObjectEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME);
		
		try {
			_log.info("Reindexing Student: " + primaryKey1 + " Elasticsearch document.");
			
			indexer.reindex(MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME, primaryKey1);
		} catch (SearchException e) {
			e.printStackTrace();
		}
	}
	
	private void reindexStudents(List<ObjectEntry> relatedObjectEntries) {
		Indexer<ObjectEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME);
		
		for (ObjectEntry objectEntry: relatedObjectEntries) {
			try {
				_log.info("Reindexing Student: " + objectEntry.getObjectEntryId() + " Elasticsearch document.");
				
				indexer.reindex(MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME, objectEntry.getObjectEntryId());
			} catch (SearchException e) {
				e.printStackTrace();
			}			
		}
	}	
	
	@Reference(unbind = "-")
	private ObjectEntryLocalService objectEntryLocalService;
	
	@Reference(unbind = "-")
	private ObjectRelationshipLocalService objectRelationshipLocalService;	
	
	private static Log _log = LogFactoryUtil.getLog(ObjectRelationshipLocalServiceServiceWrapper.class);		
}