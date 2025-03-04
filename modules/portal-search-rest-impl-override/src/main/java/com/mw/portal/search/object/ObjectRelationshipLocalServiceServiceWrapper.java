package com.mw.portal.search.object;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectRelationshipLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.service.component.annotations.Component;

@Component(service = ServiceWrapper.class)
public class ObjectRelationshipLocalServiceServiceWrapper extends ObjectRelationshipLocalServiceWrapper {

	
	@Override
	public void addObjectRelationshipMappingTableValues(
			long userId, long objectRelationshipId, long primaryKey1,
			long primaryKey2, ServiceContext serviceContext) throws PortalException {
		
		_log.debug("objectRelationshipId: " + objectRelationshipId + ", primaryKey1: " + primaryKey1 + ", primaryKey2: " + primaryKey2);
		
		super.addObjectRelationshipMappingTableValues(userId, objectRelationshipId, primaryKey1, primaryKey2, serviceContext);
		
		reindex(objectRelationshipId, primaryKey1);
	}

	@Override
	public void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1) throws PortalException {

		_log.debug("objectRelationshipId: " + objectRelationshipId + ", primaryKey1: " + primaryKey1);
		
		super.deleteObjectRelationshipMappingTableValues(objectRelationshipId, primaryKey1);
		
		reindex(objectRelationshipId, primaryKey1);
	}
	
	@Override
	public void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2) throws PortalException {
		
		_log.debug("objectRelationshipId: " + objectRelationshipId + ", primaryKey1: " + primaryKey1 + ", primaryKey2: " + primaryKey2);
		
		super.deleteObjectRelationshipMappingTableValues(objectRelationshipId, primaryKey1, primaryKey2);
		
		reindex(objectRelationshipId, primaryKey1);
	}
	
	private void reindex(long objectRelationshipId, long primaryKey1) {
		
		if (objectRelationshipId == MWConstants.MANY_TO_MANY_RELATIONSHIP_ID) {
			Indexer<ObjectEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME);
			
			try {
				indexer.reindex(MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME, primaryKey1);
			} catch (SearchException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Log _log = LogFactoryUtil.getLog(ObjectRelationshipLocalServiceServiceWrapper.class);		
}