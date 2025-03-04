Introduction:
- This is a Proof of Concept (POC) to index additional fields into Elasticsearch documents from a Many to Many Objects relationship and expose those fields to the Headless API search.
- The POC uses Company scoped entities Student and Module with a Many to Many relationship defined from Student to Module.
- A set of custom fields related to the Students mapped Modules are added to the Student Elasticsearch Document.
- If a Student to Module mapping is added the Student record is reindexed to reflect the change in the Student Elasticsearch document.
- If a Student to Module mapping is removed the Student record is reindexed to reflect the change in the Student Elasticsearch document.
- If a Module is updated, the mapped Student records are each reindexed to reflect the changes in the mapped Student Elasticsearch documents.
- If a Module is deleted, the mapped Student records are each reindexed to reflect the change in the mapped Student Elasticsearch documents.

Additional Student Elasticsearch Fields:

"moduleCount": 3
"moduleCredits": 19
"moduleDepartments": "Commerce"
"moduleIds": "34775,34778,34780"
"moduleLecturers": "Roger White, Arthur Carr, Peter Gregory"
"moduleNames": "Basis Accounting 101, Intermediate Accounting 102, Advanced Accounting 103"
"modulesOnlineOnly": false

where:

- moduleCount is the total number of Modules mapped to this Student.
- moduleCredits is the combined total of credits across all the Modules mapped to this Student.
- moduleDepartments is a comma separated, deduplicated list of departments for all the Modules mapped to this Student.
- moduleIds is a comma separated list of the ObjectEntryIds for all the Modules mapped to this Student.
- moduleLecturers is a comma separated, deduplicated list of module lecturers for all the Modules mapped to this Student.
- moduleNames is a comma separated list of module names for all the Modules mapped to this Student.
- modulesOnlineOnly is true if all of a Students Modules are online, otherwise it is false.

Environment:
- Liferay DXP version: 2025.Q1.0
- JDK: 21
- Liferay Dev Studio: Version: 3.9.8.202212271250-ga9

Setup Steps:
1. Setup a Liferay DXP
2. Go to Objects, create an Objects folder e.g. ManyToManyPOC
3. Import Objects Folder using 'Object_Folder_ManyToManyPOC_34502_20250304154433737.json'
4. Import Students and Module records
5. Blacklist Component:
com.liferay.portal.search.rest.internal.resource.v1_0.SearchResultResourceImpl
6. Update the constants in MWConstants based on the details in the environment, build and deploy the modules
7. Trigger a reindex of the Student entity records.

Modules:
1. portal-search-rest-impl-fragment / portal.search.rest.impl.fragment-1.0.0.jar: OSGi fragment module to export 'internal' packages used by MWSearchResultResourceImpl.
2. portal-search-rest-impl-override / portal.search.rest.impl.override-1.0.0.jar: Contains all the custom code:
- MWSearchResultResourceImpl: Override of OOTB class SearchResultResourceImpl to add the custom fields to the Student Headless API 'Search Result' entity.
- MWSearchResult: Override of the OOTB class SearchResult to add the custom fields to the Student Headless API 'Search Result' entity.
