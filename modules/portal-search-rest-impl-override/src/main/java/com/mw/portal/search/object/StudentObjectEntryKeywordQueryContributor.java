package com.mw.portal.search.object;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael Wall
 */
@Component(
	property = "indexer.class.name=" + MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME,
	service = KeywordQueryContributor.class
)
public class StudentObjectEntryKeywordQueryContributor
	implements KeywordQueryContributor {

	@Override
	public void contribute(
		String keywords, BooleanQuery booleanQuery,
		KeywordQueryContributorHelper keywordQueryContributorHelper) {
		
		SearchContext searchContext =
			keywordQueryContributorHelper.getSearchContext();

		queryHelper.addSearchTerm(booleanQuery, searchContext, MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_NAMES, true);
		queryHelper.addSearchTerm(booleanQuery, searchContext, MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_CODES, true);
		queryHelper.addSearchTerm(booleanQuery, searchContext, MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_LECTURERS, true);
		queryHelper.addSearchTerm(booleanQuery, searchContext, MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_DEPARTMENTS, true);
		
		queryHelper.addSearchTerm(booleanQuery, searchContext, MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_CREDITS, true); //TODO Field type...
		queryHelper.addSearchTerm(booleanQuery, searchContext, MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULES_ONLINE_ONLY, true); //TODO Field type...
		
		queryHelper.addSearchTerm(booleanQuery, searchContext, MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_COUNT, true); //TODO Field type...
		queryHelper.addSearchTerm(booleanQuery, searchContext, MWFieldConstants.STUDENT_CUSTOM_SEARCH_FIELDS.MODULE_IDS, true); //TODO Field type...
		
		_log.info("Added additional search terms for " + MWConstants.STUDENT_OBJECT_ENTRY_CLASS_NAME);
	}

	@Reference
	protected QueryHelper queryHelper;
	
	private static Log _log = LogFactoryUtil.getLog(StudentObjectEntryKeywordQueryContributor.class);
}